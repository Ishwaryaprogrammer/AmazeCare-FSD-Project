
import axios from "axios"


export const getAll = (page) => {
    return async (dispatch) => { 
      
        const response = await axios.get(`https://rickandmortyapi.com/api/character/?page=${page}`)
        // console.log(response.data)
        let action = {
            type: 'GET_ALL',
            payload: response.data
        }
        dispatch(action)
    }
}
