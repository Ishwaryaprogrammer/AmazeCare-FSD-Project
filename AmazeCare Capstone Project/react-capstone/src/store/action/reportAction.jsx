// Action functions will be called from Component so make them exportable 

import axios from "axios"

const isDoctor = localStorage.getItem("isDoctor")
// Prepare the header 
const config = {
    headers: {
        'Authorization': "Bearer " + localStorage.getItem("token")
    }
}


const getAllApi = (appId,page,size)=>
    isDoctor
        ? `http://localhost:8080/api/report/all/${appId}?page=${page}&size=${size}`
        : `http://localhost:8080/api/report/all?page=${page}&size=${size}`

const addApi = 'http://localhost:8080/api/report/add'
const updateApi =(reportid)=> `http://localhost:8080/api/report/update/${reportid}`
const deleteApi =(reportid)=> `http://localhost:8080/api/report/delete/${reportid}`

export const getAll = (appId,page,size) => {
    // action Fn must return a Fn having action object wrapped in dispatch 
    return async (dispatch) => { // Thunk gives us dispatch 
      
        const response = await axios.get(getAllApi(appId,page,size), config)
        // dispatch the action object 
        let action = {
            type: 'GET_ALL',
            payload: response.data
        }
        dispatch(action)
    }
}

export const add = (formdata) => {
    return async(dispatch) => { 
        try{
            const response = await axios.post(addApi,formdata,config)
        let action = {
            type: 'ADD',
            payload: response.data
        }
        
        
        dispatch(action)
    }catch(err){
        console.log(err.response)
    }
    }
}

export const update = (reportid,formdata) => {
    return async(dispatch) => { 
        const response = await axios.put(updateApi(reportid),formdata,config)
        let action = {
            type: 'UPDATE',
            payload: response.data
        }
        dispatch(action)
    }
}

export const deleteFunction = (reportid) => {
    return async(dispatch) => { 
        const response = await axios.delete(deleteApi(reportid), config)
        let action = {
            type: "DELETE",
            payload: reportid
        }
        dispatch(action)
    }
}



