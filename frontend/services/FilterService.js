import axios from "axios";

const FILTER_API_BASE_URL = 'http://localhost:8080/api/filters'

class FilterService{
    // Sends GET request for all filters in DB
    getFilters(){
        return axios.get(FILTER_API_BASE_URL)
    }
    addFilter(filter){
        // Sends POST request for a newly created filter
        return axios.post(FILTER_API_BASE_URL,filter)
    }
}

export default new FilterService()