import React, { Fragment, useEffect, useState } from 'react'
import { MdMailOutline, MdFace } from 'react-icons/md'
import { useDispatch, useSelector } from 'react-redux'
import { updateProfile,clearError,loadUser } from '../../actions/UserAction'
import { useAlert } from 'react-alert'
import { useNavigate } from 'react-router-dom'
import Metadata from '../layout/Metadata'
import Loader from '../layout/loader/Loader'
import { UPDATE_PROFILE_RESET } from '../../constants/UserConstatnt'
import './UpdateProfile.css'

const UpdateProfile = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const alert = useAlert()
    const { user } = useSelector((state) => state.user)
    const { loading, isUpdated, error } = useSelector((state) => state.profile)

    const [name, setName] = useState("")
    const [email, setEmail] = useState("")

    const updateProfileSubmit = (e) => {
        e.preventDefault()
        const myForm = new FormData()
        myForm.set("name", name)
        myForm.set("email", email)
        // myForm.set("avatar", avatar)
        dispatch(updateProfile(myForm))

    }

    useEffect(() => {
        if (user) {
            setName(user.name)
            setEmail(user.email)
            // setAvatar(user.avatar.url)
        }
        if (error) {
            alert.error(error)
            dispatch(clearError())
        }
        if (isUpdated) {
            alert.success("Profile updated successfully")
            dispatch(loadUser())
            navigate("/account")
            dispatch({
                type: UPDATE_PROFILE_RESET
            })
        }
    }, [dispatch, error, alert, navigate, user, isUpdated])

  return (
    <Fragment>
            {loading ? <Loader /> : <Fragment>
                <Metadata title="Update Profile" />
                <div className="updateProfileContainer">
                    <div className="updateProfileBox">
                        <h2 className='updateProfileHeading'>Update Profile</h2>
                        <form className='updateProfileForm' onSubmit={updateProfileSubmit}>
                            <div className="updateProfileName">
                                <MdFace />
                                <input type="text" name="name" placeholder='Name' required value={name} onChange={(e) => setName(e.target.value)} />
                            </div>

                            <div className="updateProfileEmail">
                                <MdMailOutline />
                                <input type="email" name="email" placeholder='Email' value={email} required onChange={(e) => setEmail(e.target.value)} readOnly />
                            </div>
                            {/* <div className="updateProfileImage" id='updateProfileImage'>
                                <img src={avatarPreview} alt="Avatar-Preview" />
                                <input type="file" name='avatar' accept='image/*' onChange={updateProfileDataChange} />
                            </div> */}
                            <input type="submit" value="Update" className='updateProfileBtn' />
                        </form>

                    </div>
                </div>

            </Fragment>}
        </Fragment>
  )
}

export default UpdateProfile