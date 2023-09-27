import React from 'react'
import './Footer.css'

const Footer = () => {
  return (
    <footer>
      <div className='leftFooter'>
        <div>
          <img src="./images/logo1.png" alt="" />
        </div>
       <div>
       <h4>Contact Information</h4>
        <p><a href="mailto:shopeasy@gmail.com">shopeasy@gmail.com</a></p>
       </div>
      </div>
      <div className="midFooter">
        <h4>Product Categories</h4>
          <p>Electronics</p>
          <p>Cloths</p>
          <p>Footwear</p>
      
          <p>Copyright 2021 &copy; ShopEasy</p>
      </div>
      <div className="rightFooter">
        <h4>Follow us on</h4>
        <a href="/">Instagram</a>
        <a href="/">LinkedIn</a>
        <a href="/">Youtube</a>
      </div>
    </footer>
  )
}

export default Footer