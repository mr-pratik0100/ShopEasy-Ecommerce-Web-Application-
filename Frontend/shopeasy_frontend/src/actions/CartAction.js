import { ADD_TO_CART, REMOVE_CART_ITEM, SAVE_SHIPPING_INFO } from '../constants/CartConstants'
import axios from 'axios'


export const addItemsToCart = (id, quantity) => async (dispatch, getState) => {
    console.log("data=== ");
    const { data } = await axios.get(`http://localhost:8080/product/${id}`)
    // const { data } = await axios.get(`/http://localhost:8080/product/${id}`)
    console.log("data=== " + data);
    dispatch({
        type: ADD_TO_CART,
        payload: {
            product: data.id,
            name: data.prodName,
            price: data.price,
            image: data.image[0].url,
            stock: data.stock,
            quantity,
        }
    })
    localStorage.setItem("cartItems", JSON.stringify(getState().cart.cartItems))
}

export const removeItemsFromQuantity = (id) => async (dispatch, getState) => {
    dispatch({
        type: REMOVE_CART_ITEM,
        payload: id
    })
    localStorage.setItem("cartItems", JSON.stringify(getState().cart.cartItems))
}

export const saveShippingInfo = (data) => async (dispatch) => {
    dispatch({
        type: SAVE_SHIPPING_INFO,
        payload: data
    })
    localStorage.setItem("shippingInfo", JSON.stringify(data))
}