import React, { Fragment, useEffect } from 'react'
import './Home.css'
import { CgMouse } from "react-icons/cg";
import ProductCard from './ProductCard';
import Metadata from '../layout/Metadata';
import { getProducts, clearErrors } from '../../actions/ProductAction';
import { useSelector, useDispatch } from 'react-redux';
import Loader from '../layout/loader/Loader';
import { useAlert } from 'react-alert';

const Home = () => {
    const dispatch = useDispatch();
    const alert=useAlert();
    const { loading, error, products, productsCount } = useSelector(state => state.products)
    console.log(products);
    useEffect(() => {
        if (error) {
            alert.error(error)
            dispatch(clearErrors())
          }
        dispatch(getProducts())
    }, [dispatch,error,alert])
    return (
        <Fragment>
           {loading ? (
            <Loader/>
           ):(
            <Fragment>
             <Metadata title="ShopEasy" />
            <div className='banner'>
                <p> ShopEasy</p>
                <h1>Find Amazing Products Below</h1>
                <a href="#container" >
                    <button> Scroll <CgMouse /> </button>
                </a>
            </div>
            <div className="homeHeading">Featured Products</div>
            <div className="container" id="container">
            {products && products.map((product) => (
                            <ProductCard product={product} key={product._id} />
                        ))}
            </div>
            </Fragment>
           )}

        </Fragment>
    )
}

export default Home