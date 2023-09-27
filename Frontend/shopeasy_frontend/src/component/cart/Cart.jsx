import React, { Fragment } from 'react'
import './Cart.css'
import CartItemsCart from './CartItemsCart'
import { useSelector, useDispatch } from 'react-redux'
import { addItemsToCart, removeItemsFromQuantity } from '../../actions/CartAction'
import { MdRemoveShoppingCart } from 'react-icons/md'
import { Typography } from '@mui/material'
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import Metadata from '../layout/Metadata'


const Cart = () => {
    const dispatch = useDispatch()
    const { cartItems } = useSelector((state) => state.cart)

    const navigate = useNavigate()

    const increaseQuantity = (id, quantity, stock) => {
        const newQty = quantity + 1;
        if (stock <= quantity) return
        dispatch(addItemsToCart(id, newQty))
    
      }

      const decreaseQuantity = (id, quantity) => {
        const newQty = quantity - 1;
        if (1 >= quantity) return
        dispatch(addItemsToCart(id, newQty))
    
      }
      const deleteCartItems = (id) => {
        dispatch(removeItemsFromQuantity(id))
    
      }
    
      const checkOutHandler = () => {
        navigate("/login?redirect=shipping")
      }
    

  return (
    <Fragment>
    <Metadata title={"CART"} />
    {cartItems.length === 0 ? (
      <div className='emptyCart'>
        <MdRemoveShoppingCart />
        <Typography>No Products in Your Cart</Typography>
        <Link to="/products">Add Products</Link>
      </div>
    ) : <Fragment>
      <div className="cartPage">
        <div className="cartHeader">
          <p>Product</p>
          <p>Quantity</p>
          <p>Subtotal</p>
        </div>

        {cartItems && cartItems.map((item) => (
          <div className="cartContainer" key={item.product}>
            <CartItemsCart item={item} deleteCartItems={deleteCartItems} />
            <div className="cartInput">
              <button onClick={() => decreaseQuantity(item.product, item.quantity)}>-</button>
              <input type="number" readOnly value={item.quantity} />
              <button onClick={() => increaseQuantity(item.product, item.quantity, item.stock)}>+</button>
            </div>
            <p className="cartSubtotal">{`₹${item.price * item.quantity}`}</p>
          </div>
        ))}

        <div className="cartgrossTotal">
          <div></div>
          <div className="cartGrossTotalBox">
            <p>Gross Total</p>
            <p>{`₹${cartItems.reduce(
              (acc, item) => acc + item.quantity * item.price, 0
            )}`}</p>
          </div>
          <div></div>
          <div className="checkOutBtn">
            <button onClick={checkOutHandler}>Check Out</button>
          </div>
        </div>
      </div>
    </Fragment>}
  </Fragment>
  )
}

export default Cart