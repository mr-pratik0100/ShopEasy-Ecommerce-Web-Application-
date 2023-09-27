import React, { Fragment, useEffect } from 'react'
import Metadata from '../layout/Metadata'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { Link } from 'react-router-dom'
import Loader from '../layout/loader/Loader'
import './Profile.css'


const Profile = () => {
    const { user, loading, isAuthenticated } = useSelector((state) => state.user)
    const navigate = useNavigate()

    useEffect(() => {
        if (isAuthenticated === false) {
            navigate("/login")
        }
    }, [navigate, isAuthenticated])

    return (
        <Fragment>

            {loading ? <Loader /> : (
                <Fragment>
                    <Metadata title={`${user.name}'s Profile`} />
                    <div className="profileContainer">
                        <div>
                     <h1>My Profile</h1>
                     <img src="../images/profileimage.jpg" alt={user.name} />
                     <Link to="/me/update">Update Profile</Link>
                 </div>
                        <div>
                            <div>
                                <h4>Full Name</h4>
                                <p>{user.name}</p>
                            </div>
                            <div>
                                <h4>Email</h4>
                                <p>{user.email}</p>
                            </div>
                            {/* <div>
                         <h4>Joined on</h4>
                         <p>{String(user.createdAt).substring(0, 10)}</p>
                     </div> */}
                            <div>
                                <Link to="/orders">My Orders</Link>
                                <Link to="/password/update">Change Password</Link>
                            </div>
                        </div>
                    </div>
                </Fragment>
            )}
        </Fragment>
    )
}

export default Profile