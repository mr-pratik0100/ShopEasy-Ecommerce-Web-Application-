import React, { Fragment, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Backdrop } from '@mui/material'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { SpeedDial, SpeedDialAction } from '@mui/material'
import { MdDashboard } from 'react-icons/md'
import { useNavigate } from 'react-router-dom'
import { useAlert } from 'react-alert'
import PersonPinIcon from '@mui/icons-material/PersonPin';
import LogoutIcon from '@mui/icons-material/Logout';
import ListAltIcon from '@mui/icons-material/ListAlt';
import { logout } from '../../../actions/UserAction';
import './UserOptions.css'

// import SpeedDial from '@mui/material/SpeedDial';
import SpeedDialIcon from '@mui/material/SpeedDialIcon';
// import SpeedDialAction from '@mui/material/SpeedDialAction';


const UserOptions = ({user}) => {

    const navigate = useNavigate()
    const [open, setOpen] = useState(false)
    const alert = useAlert()
    const dispatch = useDispatch()
    const { cartItems } = useSelector((state) => state.cart)


    const options = [
        { icon: <ListAltIcon />, name: "Orders", func: orders },
        { icon: <PersonPinIcon />, name: "Account", func: account },
        { icon: <ShoppingCartIcon style={{ color: cartItems.length > 0 ? "tomato" : "unset" }} />, name: `Cart ${cartItems.length}`, func: cart },
        { icon: <LogoutIcon />, name: "Logout", func: logOutuser }
    ]
    if (user.role === "Admin") {
        options.unshift({ icon: <MdDashboard />, name: "Dashboard", func: dashboard })
    }
    function dashboard() {
        navigate("/admin/dashboard")
    }
    function orders() {
        navigate("/orders")
    }
    function account() {
        navigate("/account")
    }
    function cart() {
        navigate("/cart")
    }
    function logOutuser() {
        dispatch(logout())
        alert.success("Logout Successfully")

    }

  return (
    <Fragment>
            <Backdrop open={open} style={{ zIndex: "10" }} />
            {/* <SpeedDial
                ariaLabel='SpeedDial tooltip example'
                onClose={() => setOpen(false)}
                onOpen={() => setOpen(true)}
                open={open}
                direction="down"
                className='speedDial'
                style={{ zIndex: '11' }}
                // icon={<AccountCircleIcon/>}
                // icon={<img
                //     className='speedDialIcon'
                //     src={user.avatar.url ? user.avatar.url : <AccountCircleIcon />}
                //     alt="Profile"
                // />}
            >
                {options.map((item) => (
                    <SpeedDialAction key={item.name} icon={item.icon} tooltipOpen={window.innerWidth <= 600 ? true : false} tooltipTitle={item.name} onClick={item.func} />
                ))}

            </SpeedDial> */}
            <SpeedDial
            className='speedDial'
        ariaLabel="SpeedDial basic example"
        onClose={() => setOpen(false)}
                onOpen={() => setOpen(true)}
        sx={{ position: 'absolute', bottom: 16, right: 16 }}
        icon={<SpeedDialIcon />}
      >
        {options.map((action) => (
          <SpeedDialAction
            key={action.name}
            icon={action.icon}
            tooltipTitle={action.name}
            onClick={action.func}
          />
        ))}
      </SpeedDial>
        </Fragment>

  )
}

export default UserOptions