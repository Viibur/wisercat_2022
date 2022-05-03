<template>
    <div class="container">
        <h1 class="header">Filter list</h1>
        <el-input v-model="filter.name" placeholder="Filter name" />
        <el-button v-on:click="addFilter()">Add Filter</el-button>
        <table>
            <tbody>
                <tr v-for = "filter in filters" v-bind:key="filter.id" v-on:click="this.clicked(filter.id)">
                    <td> {{filter.name}}</td>
                </tr>
            </tbody>
        </table>
        <p>{{show}}</p>
    </div>
</template>

<script>
import FilterService from '../../services/FilterService'
export default{
    name: 'FilterComponent',
    data(){
        return {
            filters: [],
            show: '',
            filter:{
                id: null,
                name: ''
            }
        }
    },
    methods: {
        getFilters(){
            FilterService.getFilters().then((response) => this.filters = response.data)
        },
        clicked(id){
            this.show = id
        },
        addFilter(){
            FilterService.addFilter(this.filter).then((response) => this.filters = response.data)
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
</style>
