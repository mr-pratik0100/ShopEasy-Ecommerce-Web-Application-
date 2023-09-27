import { createStore, compose, combineReducers, applyMiddleware } from 'redux'
import thunk from 'redux-thunk'
import { productDetailsReducer, productReducer } from './reducers/ProductReducer';
import { forgotPasswordreducer, profilereducer, usereducer } from './reducers/UserReducer';
import { cartreducer } from './reducers/CartReducer';
const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;


const reducer = combineReducers({
products:productReducer,
productDetails:productDetailsReducer,
user:usereducer,
profile:profilereducer,
forgotPassword:forgotPasswordreducer,
cart:cartreducer

})

let initialState = {
    cart: {
        cartItems: localStorage.getItem("cartItems") ? JSON.parse(localStorage.getItem("cartItems")) : [],
        shippingInfo: localStorage.getItem("shippingInfo") ? JSON.parse(localStorage.getItem("shippingInfo")) : {}
    }
    
};

const middleware = [thunk]

const store = createStore(reducer, initialState, composeEnhancer(applyMiddleware(...middleware)))

export default store;
