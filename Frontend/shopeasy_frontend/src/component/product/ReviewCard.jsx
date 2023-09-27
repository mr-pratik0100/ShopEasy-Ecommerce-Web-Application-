import React from 'react'
import { Rating } from '@mui/material'


const ReviewCard = ({review}) => {
    const options = {
        size: "medium",
        value:review.rating,
        readOnly: true,    
        precision: 0.5,    
    }
  return (
    <div className='reviewcard'>
    <img src="../images/profileimage.jpg" alt="User" />
    <p>{review.name}</p>
    <Rating {...options} />
    <span className='reviewCard-span'>{review.comment}</span>
</div>
  )
}

export default ReviewCard