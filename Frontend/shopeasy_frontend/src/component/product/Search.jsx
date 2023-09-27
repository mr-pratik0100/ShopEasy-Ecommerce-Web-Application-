import React,{ Fragment, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Metadata from '../layout/Metadata'
import './Search.css'

const Search = () => {
    const [keyword, setKeyword] = useState("")
    const navigate = useNavigate()
    const submitHandler = (e) => {
        e.preventDefault()
        if (keyword.trim()) {
            navigate(`/products/${keyword}`)
        } else {
            navigate('/products')
        }

    }
  return (
    <Fragment>
        <Metadata title="SEARCH A PRODUCT -- ECOMMERCE" />
            <form className='searchBox' onSubmit={submitHandler}>
                <input type="text" placeholder='Search a product...' onChange={(e) => setKeyword(e.target.value)} />
                <input type="submit" value="Search" />
            </form>
    </Fragment>
  )
}

export default Search