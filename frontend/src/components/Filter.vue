<template>
    <h1 class="header">Filter list</h1>
    <div id="formInput">
        <el-form>
            <el-form-item label="Filter name">
                <el-input v-model="filterElem.name" />
            </el-form-item>
            <el-form-item v-for="criteriaItem in filterElem.criteria" v-bind:key="criteriaItem.id">
                <p>{{criteriaItem}}</p>
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
            <h3>{{filterElem.name}}</h3>
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
                criteria: []
            }
        }
    },
    methods: {
        getFilters(){
            FilterService.getFilters().then((response) => this.filters = response.data)
        },
        clicked(filter){
            const parent = document.getElementById("renderedFilter");
            parent.textContent = '';

            const parsable = filter.criteria
            for (let index = 0; index < parsable.length; index++) {
                const element = parsable[index];
                if (element.type == "Amount"){                    
                    const defaultNumber = document.createElement("input");
                    defaultNumber.type = "number";
                    defaultNumber.value = element.defaultValue;
                    parent.appendChild(defaultNumber);

                    const values = ["More than","Less than","Equal"];
                    const comparingConditions = document.createElement("select");
                    for (const val of values){
                        const option = document.createElement("option");
                        option.value = val;
                        option.text = val;
                        comparingConditions.appendChild(option);
                    }
                    parent.appendChild(comparingConditions);

                    const inputNumber = document.createElement("input");
                    inputNumber.type = "number";
                    parent.appendChild(inputNumber);
                    parent.appendChild(document.createElement("br"))
                }
                else if (element.type == "Title"){
                    const defaultTitle = document.createElement("input");
                    defaultTitle.type = "text";
                    defaultTitle.value = element.defaultValue;
                    parent.appendChild(defaultTitle);

                    const values = ["Starts with","Ends with","Contains"];
                    const comparingConditions = document.createElement("select");
                    for (const val of values){
                        const option = document.createElement("option");
                        option.value = val;
                        option.text = val;
                        comparingConditions.appendChild(option);
                    }
                    parent.appendChild(comparingConditions);

                    const inputTitle = document.createElement("input");
                    inputTitle.type = "text";
                    parent.appendChild(inputTitle);
                    parent.appendChild(document.createElement("br"))
                }
                else if (element.type == "Date"){
                    const defaultTitle = document.createElement("input");
                    defaultTitle.type = "date"
                    parent.appendChild(defaultTitle);

                    parent.appendChild(document.createElement("br"))
                }
            }
        },
        showForm(){
            document.getElementById("formInput").style.display = "flex"
        },
        hideForm(){
            document.getElementById("formInput").style.display = "none"
        },
        addFilter(){
            FilterService.addFilter(this.filterElem).then((response) => this.filters = response.data)
            this.hideForm()
        },
        newCriteria(){
        },
    },
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
</style>
