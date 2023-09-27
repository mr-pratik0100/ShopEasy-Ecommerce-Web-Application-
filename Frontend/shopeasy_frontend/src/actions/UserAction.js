import axios from "axios"
import { LOGIN_FAIL,LOGIN_SUCCESS,LOGIN_REQUEST,CLEAR_ERRORS,
REGISTER_FAIL,REGISTER_REQUEST,REGISTER_SUCCESS,
LOAD_USER_FAIL, LOAD_USER_REQUEST, LOAD_USER_SUCCESS,  LOGOUT_SUCCESS,
LOGOUT_FAIL,
UPDATE_PROFILE_FAIL,UPDATE_PROFILE_REQUEST,UPDATE_PROFILE_RESET,UPDATE_PROFILE_SUCCESS,
UPDATE_PASSWORD_FAIL,UPDATE_PASSWORD_REQUEST,UPDATE_PASSWORD_SUCCESS,UPDATE_PASSWORD_RESET,
FORGOT_PASSWORD_FAIL,FORGOT_PASSWORD_REQUEST,FORGOT_PASSWORD_SUCCESS,
RESET_PASSWORD_FAIL,RESET_PASSWORD_REQUEST,RESET_PASSWORD_SUCCESS
} from "../constants/UserConstatnt"
import Cookies from "js-cookie"

export const login = (email, password) => async (dispatch) => {

    try {
        dispatch({ type: LOGIN_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }

        const { data } = await axios.post(
            "http://localhost:8080/user/login",
            { email, password },
            config
        )
            Cookies.set("userr",data.id,{expires:7})

        dispatch({ type: LOGIN_SUCCESS, payload: data})

    } catch (err) {
        dispatch({ type: LOGIN_FAIL, payload: err.response.data.message })

    }
}

export const logout = () => async (dispatch) => {
    try {
        await axios.get(`http://localhost:8080/user/logout`)
        dispatch({ type: LOGOUT_SUCCESS })
    } catch (err) {
        dispatch({ type: LOGOUT_FAIL, payload: err.response.data.message })
    }
}

export const register = (userData) => async (dispatch) => {
    try {
        dispatch({ type: REGISTER_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }
        const { data } = await axios.post(`http://localhost:8080/user/register`, userData, config)
        dispatch({ type: REGISTER_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: REGISTER_FAIL, payload: err.response.data.message })
    }
}

export const loadUser = () => async (dispatch) => {
    try {
        dispatch({ type: LOAD_USER_REQUEST })
        const { data } = await axios.get(`http://localhost:8080/user/me`)
        dispatch({ type: LOAD_USER_SUCCESS, payload: data })


    } catch (err) {
        dispatch({ type: LOAD_USER_FAIL, payload: err.response.data.message })
    }
}


export const updateProfile = (userData) => async (dispatch) => {
    try {
        dispatch({ type: UPDATE_PROFILE_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }
        const { data } = await axios.put(`http://localhost:8080/user/me/update`, userData, config)
        dispatch({ type: UPDATE_PROFILE_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: UPDATE_PROFILE_FAIL, payload: err.response.data.message })
    }
}

export const updatePassword = (passwords) => async (dispatch) => {
    try {
        dispatch({ type: UPDATE_PASSWORD_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }
        const { data } = await axios.put(`http://localhost:8080/user/password/update`, passwords, config)
        dispatch({ type: UPDATE_PASSWORD_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: UPDATE_PASSWORD_FAIL, payload: err.response.data.message })
    }
}

export const forgotPassword = (email) => async (dispatch) => {
    try {
        dispatch({ type: FORGOT_PASSWORD_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }
        const { data } = await axios.post(
            `http://localhost:8080/user/password/forgot`,
            email,
            config
        )
        dispatch({ type: FORGOT_PASSWORD_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: FORGOT_PASSWORD_FAIL, payload: err.response.data.message })
    }
}

export const resetPassword = (token, passwords) => async (dispatch) => {
    try {
        dispatch({ type: RESET_PASSWORD_REQUEST })
        const config = { headers: { "Content-Type": "application/json" } }
        const { data } = await axios.put(
            `http://localhost:8080/user/password/reset/${token}`,
            passwords,
            config
        )
        dispatch({ type: RESET_PASSWORD_SUCCESS, payload: data })
    } catch (err) {
        dispatch({ type: RESET_PASSWORD_FAIL, payload: err.response.data.message })
    }
}


export const clearError = () => async (dispatch) => {
    dispatch({ type: CLEAR_ERRORS })
}