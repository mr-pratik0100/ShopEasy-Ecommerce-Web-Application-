import axios from 'axios'
import {
    ALL_PRODUCT_SUCCESS, ALL_PRODUCT_REQUEST, ALL_PRODUCT_FAIL, CLEAR_ERRORS,
    PRODUCT_DETAILS_REQUEST, PRODUCT_DETAILS_SUCCESS, PRODUCT_DETAILS_FAIL,


} from '../constants/ProductConstatnts'

export const getProducts = (keyword = "", category) => async (dispatch) => {
    try {
        dispatch({ type: ALL_PRODUCT_REQUEST })

        let link = `http://localhost:8080/product/all?keyword=${keyword}`
        if (category) {
            link = `http://localhost:8080/product/all?category=${category}`
        }
        const { data } = await axios.get(link)
        dispatch({
            type: ALL_PRODUCT_SUCCESS,
            payload: data
        })

    } catch (err) {
        dispatch({
            type: ALL_PRODUCT_FAIL,
            payload: err.response.data.message
        })
    }
}

export const getProductDetails = (id) => async (dispatch) => {
    try {

        dispatch({ type: PRODUCT_DETAILS_REQUEST })
        const { data } = await axios.get(`http://localhost:8080/product/${id}`)
        dispatch({
            type: PRODUCT_DETAILS_SUCCESS,
            payload: data
        })
    } catch (err) {
        dispatch({
            type: PRODUCT_DETAILS_FAIL,
            payload: err.response.data.message
        })
    }

}

export const clearErrors = () => async (dispatch) => {
    dispatch({ type: CLEAR_ERRORS })
}