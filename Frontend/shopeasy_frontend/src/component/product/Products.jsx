import React, { Fragment, useEffect, useState } from 'react'
import './Products.css'
import { useParams } from 'react-router-dom'
import { Slider } from '@mui/material'
import { Typography } from '@mui/material'
import { useAlert } from 'react-alert'
import Pagination from 'react-js-pagination'
import Loader from '../layout/loader/Loader'
import { useSelector, useDispatch } from 'react-redux'
import { clearErrors, getProducts } from '../../actions/ProductAction'
import Metadata from '../layout/Metadata'
import ProductCard from '../home/ProductCard'

const Products = () => {
    const dispatch = useDispatch()
    const params = useParams()
    const [currentPage, setCurrentPage] = useState(1)
    const [price, setPrice] = useState([0, 25000])
    const [category, setCategory] = useState("")
    const [ratings, setRatings] = useState(0);

    const alert = useAlert()

    const { products, loading, error, productsCount } = useSelector((state) => state.products)

    // const setCurrentPageNo = (e) => {
    //     setCurrentPage(e)
    // }
    const priceHandler = (event, newPrice) => {
        setPrice(newPrice)
    }
    const categories = [
        "Footwear",
        "Cloths",
        "Electronics"
    ]

    useEffect(() => {
        if (error) {
            alert.error(error)
            dispatch(clearErrors())
        }
        dispatch(getProducts(params.keyword, category))
    }, [dispatch, params.keyword, category,alert,error])
    return (
        <Fragment>
            {loading ? <Loader /> : (
                <Fragment>
                    <Metadata title="PRODUCTS -- ShopEasy" />
                    <h2 className="productsHeading">Products</h2>
                    <div className="products">
                        {products && products.map((product) => (
                            <ProductCard key={product.id} product={product} />
                        ))}
                    </div>
                    <div className="filterBox">
                        {/* <Typography>Price</Typography>
                        <Slider value={price}
                            onChangeCommitted={priceHandler}
                            valueLabelDisplay="auto"
                            aria-labelledby='range-slider'
                            min={0}
                            max={25000}
                            size="small"
                        /> */}
                        <Typography>Categories</Typography>
                        <ul className="categotyBox">
                            {categories.map((category) => (
                                <li className='category-link cat' key={category} onClick={() => setCategory(category)}>{category}</li>
                            ))}
                        </ul>


                        {/* <fieldset>
                            <Typography component="legend">Ratings Above</Typography>
                            <Slider
                                value={ratings}
                                onChange={(e, newRating) => {
                                    setRatings(newRating);
                                }}
                                aria-labelledby="continuous-slider"
                                valueLabelDisplay="auto"
                                min={0}
                                max={5}
                            />
                        </fieldset> */}


                        {/* <button className='reset' onClick={() => window.location.reload()} >Reset</button> */}
                    </div>
                    {/* <div className="paginationBox">
                        <Pagination shape="rounded" activePage={currentPage} itemsCountPerPage={8} totalItemsCount={productsCount} onChange={setCurrentPageNo} nextPageText="Next" prevPageText="Prev" firstPageText="1st" lastPageText="last" itemClass='page-item' linkClass='page-link' activeClass='pageItemActive' activeLinkClass='pageLinkActive' />
                    </div> */}
                </Fragment>
            )}
        </Fragment>
    )
}

export default Products