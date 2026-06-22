
const initialState = {
    list: [],
    totalPages: 0,
    totalElements: 0
}
export const listReducer = (state = initialState, action) => {
    if (action.type === 'GET_ALL') {
        return {
            ...state,
            list: action.payload.results,
            totalPages: action.payload.info.pages,
            totalElements: action.payload.info.count
        }
    }
    return state
}

