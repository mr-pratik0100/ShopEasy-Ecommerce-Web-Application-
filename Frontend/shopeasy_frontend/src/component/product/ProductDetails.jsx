import React, { Fragment, useEffect, useState } from 'react'
import './ProductDetails.css'
import Carousel from 'react-material-ui-carousel'
import { useSelector, useDispatch } from 'react-redux'
import { getProductDetails,clearErrors } from '../../actions/ProductAction'
import { useParams } from "react-router-dom";
import { Dialog, DialogActions, DialogContent, DialogTitle, Button, Rating } from '@mui/material'
import ReviewCard from './ReviewCard'
import Loader from '../layout/loader/Loader'
import { useAlert } from 'react-alert'
import Metadata from '../layout/Metadata'
import { addItemsToCart } from '../../actions/CartAction'






const ProductDetails = () => {
    const params = useParams()
    const alert = useAlert()
    const dispatch = useDispatch()
    const [open, setOpen] = useState(false)
    const [rating, setRating] = useState(0)
    const [comment, setComment] = useState("")
    const { product, loading, error } = useSelector(state => state.productDetails)

    const options = {
        value: product.rating,
        readOnly: true,
        precision: 0.5
    }

    const [quantity, setQuantity] = useState(1)

    const increaseQuantity = () => {
        if (product.stock <= quantity) return
        const qty = quantity + 1;
        setQuantity(qty)
        
    }

    const decreaseQuantity = () => {
        if (1 >= quantity) return
        const qty = quantity - 1;
        setQuantity(qty)

    }

    const addToCartHandler = () => {
        dispatch(addItemsToCart(params.id, quantity))
        alert.success("Item Added To Cart")
        console.log(params.id);
    }
    const submitReviewToggle = () => {
        open ? setOpen(false) : setOpen(true)
    }

    const reviewSubmitHandler = () => {
        const myform = new FormData()
        myform.set('rating', rating)
        myform.set('comment', comment)
        myform.set('productId', params.id)
        // dispatch(newReview(myform))
        setOpen(false)
    }

    useEffect(() => {
        if (error) {
          alert.error(error)
          dispatch(clearErrors())
        }
        // if (reviewError) {
        //   alert.error(reviewError)
        //   dispatch(clearErrors())
        // }
        // if (success) {
        //   alert.success("Review Submitted Successfully")
        //   dispatch({ type: NEW_REVIEW_RESET })
        // }
        dispatch(getProductDetails(params.id))
      }, [dispatch, params.id, error, alert])
    
    
    return (
       <Fragment>
        {loading? <Loader/>:(
             <Fragment>
                <Metadata title={`${product.prodName} -- ShopEasy`}/>
             <div className="ProductDetails">
                 <div>
                     <Carousel className='carousel'>
                         {product.image?.map((item, i) => (
                             <img className='CarouselImage' key={i} src={item.url} alt={`${i} Slide`} />
                         ))}
                     </Carousel>
                 </div>
 
                 <div className='name3'>
                     <div className="detailsBlock-1">
                         <h2>{product.prodName}</h2>
                         <p>Product # {product.id}</p>
                     </div>
                     <div className="detailsBlock-2">
                         <Rating {...options} />
                         <span className="detailsBlock-2-span">
                             ({product.numOfReviews} Reviews)
                         </span>
                     </div>
                     <div className="detailsBlock-3">
                         <h1>{`₹${product.price}`}</h1>
                         <div className="detailsBlock-3-1">
                             <div className="detailsBlock-3-1-1">
                                 <button onClick={decreaseQuantity}>-</button>
                                 <input readOnly type="number" value={quantity} />
                                 <button onClick={increaseQuantity}>+</button>
                             </div>
                             <button
                                 disabled={product.stock < 1 ? true : false}
                                 onClick={addToCartHandler}
                             >
                                 Add to Cart
                             </button>
                         </div>
 
                         <p>
                             Status:
                             <b className={product.stock < 1 ? "redColor" : "greenColor"}>
                                 {product.stock < 1 ? " OutOfStock" : " InStock"}
                             </b>
                         </p>
                     </div>
                     <div className="detailsBlock-4">
                         Description : <p>{product.description}</p>
                     </div>
                     <button className="submitReview" onClick={submitReviewToggle}>
                         Submit Review
                     </button>
 
                 </div>
             </div>
             <h3 className='reviewsHeading'>Reviews</h3>
             {/* <Dialog
                 aria-labelledby='simple-dialog-title'
                 open={open}
                 onClose={submitReviewToggle}
             >
                 <DialogTitle>Submit Review</DialogTitle>
                 <DialogContent className='submitDialog'>
                     <Rating onChange={(e) => setRating(e.target.value)} value={rating} size='large' />
                     <textarea className='submitDialogTextArea' cols="30" rows="5" value={comment} onChange={(e) => setComment(e.target.value)}></textarea>
                 </DialogContent>
                 <DialogActions>
                     <Button color='secondary' onClick={submitReviewToggle}>Cancel</Button>
                     <Button color='primary' onClick={reviewSubmitHandler}>Submit</Button>
                 </DialogActions>
 
             </Dialog> */}
 
             {product.review && product.review[0] ? (
                 <div className='reviews'>{product.review &&
                     product.review.map((review) => <ReviewCard review={review} />)
                 }</div>
             ) : (
                 <p className='noReviews'>No Reviews</p>
             )}
 
         </Fragment>
        )}
       </Fragment>
    )
}

export default ProductDetails