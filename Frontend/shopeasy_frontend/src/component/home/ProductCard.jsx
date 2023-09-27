import React from 'react'
import { Link } from 'react-router-dom'
import { Rating } from '@mui/material'




const ProductCard = ({ product }) => {
    const options = {
        precision: 0.5,
        size: "medium",
        value: product.rating,
        readOnly: true,
    }
    return (
        <Link className='productCard' to={`/product/${product.id}`}>
            <img src={product.image[0].url} alt={product.name} />
            <p>{product.prodName}</p>
            <div>
                <Rating {...options} />
                {/* <span className='rv'>(256 Reviews)</span> */}
            </div>
            <span>{`₹${product.price}`}</span>
        </Link>
    )
}

export default ProductCard