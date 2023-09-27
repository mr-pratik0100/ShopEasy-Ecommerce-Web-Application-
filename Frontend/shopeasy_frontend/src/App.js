import { useEffect } from 'react';
import './App.css';
import Header from './component/layout/header/Header';
import { BrowserRouter,Routes,Route,Navigate } from 'react-router-dom';
import webfont from 'webfontloader'
import Footer from './component/layout/footer/Footer';
import Home from './component/home/Home';
import ProductDetails from './component/product/ProductDetails';
import Products from './component/product/Products';
import Search from './component/product/Search';
import LoginSignup from './component/user/LoginSignup';
import store from './Store';
import { loadUser } from './actions/UserAction';
import UserOptions from './component/layout/header/UserOptions';
import { useSelector } from 'react-redux';
import Profile from './component/user/Profile';
import UpdateProfile from './component/user/UpdateProfile';
import UpdatePassword from './component/user/UpdatePassword';
import ForgotPassword from './component/user/ForgotPassword';
import ResetPassword from './component/user/ResetPassword';
import Cart from './component/cart/Cart';
import Shipping from './component/cart/Shipping';

function App() {

  const { isAuthenticated, user } = useSelector((state) => state.user)


  useEffect(() => {
    webfont.load({
      google: {
        families: ['Roboto', 'Droid Sans', 'Chilanka']
      }
    })
    store.dispatch(loadUser())
  }, [])

  return (
    <div className="App">
      <BrowserRouter>

        <Header />
        {isAuthenticated && <UserOptions user={user}/>}
        <Routes>
        <Route exact path='/' element={<Home />} />
        <Route exact path='/product/:id' element={<ProductDetails />} />
        <Route exact path='/products' element={<Products />} />
        <Route exact path='/products/:keyword' element={<Products />} />
        <Route exact path='/search' element={<Search />} />
        <Route exact path='/login' element={<LoginSignup />} />
        <Route exact path='/account' element={isAuthenticated ? <Profile /> : <Navigate to="/login" />} />
        <Route exact path='/me/update' element={isAuthenticated ? <UpdateProfile /> : <Navigate to="/login" />} />
        <Route exact path='/password/update' element={isAuthenticated ? <UpdatePassword /> : <Navigate to="/login" />} />
        <Route exact path='/password/forgot' element={<ForgotPassword />} />
        <Route exact path='/password/reset/:token' element={<ResetPassword />} />
        <Route exact path='/cart' element={<Cart />} />
        <Route exact path='/shipping' element={isAuthenticated ? <Shipping /> : <Navigate to="/login" />} />
        

        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;
