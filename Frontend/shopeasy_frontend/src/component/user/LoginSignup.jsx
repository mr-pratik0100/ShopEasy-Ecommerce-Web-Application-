import React, { Fragment, useEffect, useRef, useState } from 'react'
import Loader from '../layout/loader/Loader'
import { MdMailOutline, MdLockOpen, MdFace } from 'react-icons/md'
import { Link } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
// import { clearError, login, register } from '../../actions/UserAction'
import { login, clearError,register } from '../../actions/UserAction'
import { useAlert } from 'react-alert'
import { useNavigate } from 'react-router-dom'
import { useLocation } from 'react-router-dom'

import './LoginSignup.css'
import Metadata from '../layout/Metadata'

const LoginSignup = () => {
    const navigate = useNavigate()
    const location = useLocation()
    const dispatch = useDispatch()
    const alert = useAlert()

    const loginTab = useRef(null)
    const registerTab = useRef(null)
    const switcherTab = useRef(null)


    const [loginEmail, setLoginEmail] = useState("")
    const [loginPassword, setLoginPassword] = useState("")
    // const [avatar, setAvatar] = useState()
    // const [avatarPreview, setAvatarPreview] = useState("../images/profileimage.jpg")

    const { error, loading, isAuthenticated } = useSelector((state) => state.user)


    const [user, setUser] = useState({
        name: "",
        email: "",
        password: ""
    })
    const { name, email, password } = user


    const switchTabs = (e, tab) => {
        if (tab === "login") {
            switcherTab.current.classList.add("shiftToNeutral")
            switcherTab.current.classList.remove("shiftToRight")

            registerTab.current.classList.remove("shiftToNeutralForm")
            loginTab.current.classList.remove("shiftToLeft")
        }
        if (tab === "register") {
            switcherTab.current.classList.add("shiftToRight")
            switcherTab.current.classList.remove("shiftToNeutral")

            registerTab.current.classList.add("shiftToNeutralForm")
            loginTab.current.classList.add("shiftToLeft")
        }
    }

    const loginSubmit = (e) => {
        e.preventDefault()
        dispatch(login(loginEmail, loginPassword));
    }


    const registerSubmit = (e) => {
        e.preventDefault()
        const myForm = new FormData()
        myForm.set("name", name)
        myForm.set("email", email)
        myForm.set("password", password)
        // myForm.set("avatar", avatar)
        dispatch(register(myForm))

    }

    const registerDataChange = (e) => {
        // if (e.target.name === "avatar") {
        //     const reader = new FileReader()
        //     reader.onload = () => {
        //         if (reader.readyState === 2) {
        //             // setAvatarPreview(reader.result)
        //             // setAvatar(reader.result)
        //         }
        //     }
            // reader.readAsDataURL(e.target.files[0]);

        // } else {
            setUser({ ...user, [e.target.name]: e.target.value })
        // }
    }

    const redirect = location.search ? location.search.split("=")[1] : "account"

    useEffect(() => {
        if (error) {
            alert.error(error)
            dispatch(clearError())
        }
        if(isAuthenticated){
            navigate(`/${redirect}`)
        }
    }, [dispatch, error, alert,isAuthenticated])


    return (
        <Fragment>

            {loading ? <Loader /> : (
                <Fragment>
                    <Metadata title="Login-Signup"/>
                    <div className="loginSignupContainer">
                        <div className="loginSignupBox">
                            <div>
                                <div className="login_signup_tpggle">
                                    <p onClick={(e) => switchTabs(e, "login")}>LOGIN</p>
                                    <p onClick={(e) => switchTabs(e, "register")}>REGISTER</p>
                                </div>
                                <button ref={switcherTab}></button>
                            </div>
                            <form className='loginForm' ref={loginTab} onSubmit={loginSubmit}>
                                <div className="loginEmail">
                                    <MdMailOutline />
                                    <input type="email" placeholder='Email' value={loginEmail} required onChange={(e) => setLoginEmail(e.target.value)} />
                                </div>
                                <div className="loginPassword">
                                    <MdLockOpen />
                                    <input type="password" placeholder='Password' required value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)} />
                                </div>
                                <Link to="/password/forgot">Forgot Password</Link>
                                <input type="submit" value="Login" className='loginBtn' />
                            </form>
                            <form className='signupForm' ref={registerTab} onSubmit={registerSubmit}>
                                <div className="signUpName">
                                    <MdFace />
                                    <input type="text" name="name" placeholder='Name' required value={name} onChange={registerDataChange} />
                                </div>

                                <div className="signUpEmail">
                                    <MdMailOutline />
                                    <input type="email" name="email" placeholder='Email' value={email} required onChange={registerDataChange} />
                                </div>
                                <div className="signUpPassword">
                                    <MdLockOpen />
                                    <input type="password" name="password" placeholder='Password' required value={password} onChange={registerDataChange} />
                                </div>
                                {/* <div className="registerImage" id='registerImage'>
                                    <img src={avatarPreview} alt="Avatar-Preview" />
                                    <input type="file" name='avatar' accept='image/*' onChange={registerDataChange} />
                                </div> */}
                                <input type="submit" value="Register" className='signBtn' />
                            </form>
                        </div>
                    </div>
                </Fragment>
            )}
        </Fragment>
    )
}

export default LoginSignup