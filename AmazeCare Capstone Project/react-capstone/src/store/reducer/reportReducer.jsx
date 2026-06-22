// Define Initial State 
const initialState = {
    reports: [],
    totalPages: 0,
    totalElements: 0
}
// Inject state and action in reducer and initialize state with
// initial value
export const reportReducer = (state = initialState, action) => {
    if (action.type === 'GET_ALL') {
        return {
            ...state,
            reports: action.payload.list,
            totalPages: action.payload.totalPage,
        totalElements: action.payload.totalElements
        }
    }
    if (action.type === "ADD") {
        return {
            ...state,
            reports: [...state.reports, action.payload]
        }
    }

    if (action.type === 'DELETE') {
        return {
            ...state,
            reports: state.reports.filter(
                report => report.id !== action.payload
            )
        }
    }

    if (action.type === "UPDATE") {
    return {
        ...state,
        reports: state.reports.list.map(report =>
            report.id === action.payload.id
                ? action.payload
                : report
        )
    }
}
    return state
}



/**
 action is expected to have following structure
 action = {
    type: ''
    payload: ''
 }

 return {
        ...state, //making a clone to replace earlier immutable state to new state 
        incidents: action.payload   // attach data(payload) to incidents in store 
            }

ex. Adding a value to array 
state.person = [p1,p2,p3]
add p4 to this array
person =  [...state.person, p4]  : a new array with [p1,p2,p3,p4]
 */