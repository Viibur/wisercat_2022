<template>
    <h1 class="header">Filter list</h1>
    <div id="formInput">
        <el-form>
            <el-form-item label="Filter name">
                <el-input v-model="filterElem.name" />
            </el-form-item>
            <el-form-item v-for="criteriaItem in filterElem.criteria" v-bind:key="criteriaItem.id">
                <div v-if="criteriaItem.type == 'Amount'">
                    <select @change="changeItemType($event,criteriaItem)">
                        <option selected text="Amount" value="Amount" />
                        <option text="Title" value="Title"/>
                        <option text="Date" value="Date"/>
                    </select>
                    <select @change="changeItemOption($event,criteriaItem)">
                        <option value="More than" text="More than" />
                        <option value="Equals" text="Equals"/>
                        <option value="Less than" text="Less than"/>
                    </select>
                    <input type="number" title="Default Value" @change="changeItemDefault($event,criteriaItem)">
                </div>
                <div v-if="criteriaItem.type == 'Title'">
                     <select @change="changeItemType($event,criteriaItem)">
                        <option text="Amount" value="Amount" />
                        <option selected text="Title" value="Title"/>
                        <option text="Date" value="Date"/>
                    </select>
                    <select @change="changeItemOption($event,criteriaItem)">
                        <option value="Starts with" text="Starts with" />
                        <option value="Contains" text="Contains"/>
                        <option value="Ends with" text="Ends with"/>
                    </select>
                    <input type="text" title="Default Value" @change="changeItemDefault($event,criteriaItem)">
                </div>
                <div v-if="criteriaItem.type == 'Date'">
                     <select @change="changeItemType($event,criteriaItem)">
                        <option text="Amount" value="Amount" />
                        <option text="Title" value="Title"/>
                        <option selected text="Date" value="Date"/>
                     </select>
                </div>
                <el-button type="danger" @click="deleteCriteria(criteriaItem)">X</el-button>
            </el-form-item>
            <el-form-item>
                <el-button @click="newCriteria()">Add row</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addFilter()">Create</el-button> 
                <el-button @click="hideForm()">Cancel</el-button> 
            </el-form-item>
        </el-form>
    </div>
    <div class="container">
        <el-button v-on:click="showForm()">Add Filter</el-button>
        <table>
            <tbody>
                <tr v-for = "filter in filters" v-bind:key="filter.id" v-on:click="this.clicked(filter)">
                    <td> {{filter.name}}</td>
                </tr>
            </tbody>
        </table>
        <div>
            <el-form id="renderedFilter">
                
            </el-form>
        </div>
    </div>
</template>

<script>
import FilterService from '../../services/FilterService'
export default{
    name: 'FilterComponent',
    data(){
        return {
            filters: [],
            filterElem:{
                id: null,
                name: '',
                criteria: [],
            }
        }
    },
    methods: {
        // Makes GET request to DB for all of the filters
        getFilters(){
            FilterService.getFilters().then((response) => this.filters = response.data)
        },
        // Sends POST request to DB to save filter
        addFilter(){
            if(this.filterElem.criteria.length > 0 && this.filterElem.name.length > 0){
                FilterService.addFilter(this.filterElem).then((response) => this.filters = response.data)
            }
            this.hideForm()
        },
        // Render all existing filter criteria for that filter on page
        clicked(filter){
            const parent = document.getElementById("renderedFilter");
            parent.textContent = '';

            // All of the criteria that belong to that filter
            const parsable = filter.criteria
            for (let index = 0; index < parsable.length; index++) {
                const element = parsable[index];
                // If the criteria is amount
                if (element.type == "Amount"){ 
                    // Make number input field                   
                    const inputNumber = document.createElement("input");
                    inputNumber.type = "number";
                    inputNumber.text = "Input Number"
                    parent.appendChild(inputNumber);

                    // Make option selector for all three predefined values
                    const values = ["More than","Less than","Equals"];
                    const comparingConditions = document.createElement("select");
                    for (const val of values){
                        const option = document.createElement("option");
                        option.value = val;
                        option.text = val;
                        comparingConditions.appendChild(option);
                    }
                    //Sets the user picked default option
                    comparingConditions.value = element.option
                    parent.appendChild(comparingConditions);

                    //Sets the user picked default option and makes it immutable
                    const defaultNumber = document.createElement("input");
                    defaultNumber.type = "number";
                    defaultNumber.readOnly = "readonly"
                    defaultNumber.value = element.defaultValue
                    parent.appendChild(defaultNumber);
                    parent.appendChild(document.createElement("br"))
                }
                // If the criteria is title
                else if (element.type == "Title"){
                    // Make title input field   
                    const inputTitle = document.createElement("input");
                    inputTitle.type = "text";
                    inputTitle.text = "Input Title"
                    parent.appendChild(inputTitle);

                    // Make option selector for all three predefined values
                    const values = ["Starts with","Ends with","Contains"];
                    const comparingConditions = document.createElement("select");
                    for (const val of values){
                        const option = document.createElement("option");
                        option.value = val;
                        option.text = val;
                        comparingConditions.appendChild(option);
                    }
                    //Sets the user picked default option
                    comparingConditions.value = element.option
                    parent.appendChild(comparingConditions);

                    //Sets the user picked default option and makes it immutable
                    const defaultTitle = document.createElement("input");
                    defaultTitle.type = "text";
                    defaultTitle.value = element.defaultValue
                    defaultTitle.readOnly = "readonly"
                    parent.appendChild(defaultTitle);
                    parent.appendChild(document.createElement("br"))
                }
                // If the criteria is Date
                else if (element.type == "Date"){
                    // Make date input field
                    const defaultTitle = document.createElement("input");
                    defaultTitle.type = "date"
                    parent.appendChild(defaultTitle);

                    parent.appendChild(document.createElement("br"))
                }
            }
        },
        // Resets and makes filter creation form visible
        showForm(){
            this.filterElem.name = ''
            this.filterElem.criteria = []
            document.getElementById("formInput").style.display = "flex"
        },
        // Hides form after cancellation or filter creation
        hideForm(){
            document.getElementById("formInput").style.display = "none"
        },
        // Appends default criteria on new criteria creation in filter creation form
        newCriteria(){
            this.filterElem.criteria.push({type: "Amount",option: "More than",defaultValue: "0"})
        },
        // If user changes criteria type(Number -> Title), changes it for that criteria item as well
        changeItemType(event, criteriaItem){
            criteriaItem.type = event.target.value
        },
        // If user changes criteria default option(More Than -> Equals), changes it for that criteria item as well
        changeItemOption(event, criteriaItem){
            criteriaItem.option = event.target.value
        },
        // If user changes criteria default value("" -> "Hello, "), changes it for criteria item aswell
        changeItemDefault(event, criteriaItem){
            criteriaItem.defaultValue = event.target.value
        },
        deleteCriteria(criteriaItem){
            const index = this.filterElem.criteria.indexOf(criteriaItem)
            this.filterElem.criteria.splice(index,1)
        }
    },
    // On page render, show all existing filters
    created() {
        this.getFilters()
    }
}
</script>

<style>
table {
    text-align: left;
}
#formInput{
    display: none;
}
input{
    height: 30px;
    width: 100px;
    padding: auto;
}
select{
    height: 30px;
    width: 100px;
}
</style>
