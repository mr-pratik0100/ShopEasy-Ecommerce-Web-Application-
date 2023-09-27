import React from 'react'
import { ReactNavbar } from "overlay-navbar";
import {MdAccountCircle} from "react-icons/md";
import {MdSearch} from "react-icons/md";
import {MdAddShoppingCart} from "react-icons/md";

const options = {
  burgerColorHover: "#eb4034",
  logo:'./images/logo1.png',
  logoWidth: "13vmax",
  navColor1: "#232F3E",
  logoHoverSize: "10px",
  logoHoverColor: "#dcc57084",
  link1Text: "Home",
  link2Text: "Products",
  link3Text: "Contact",
  link4Text: "About", 
  link1Url: "/",
  link2Url: "/products",
  link3Url: "/contact",
  link4Url: "/about",
  link1Size: "1.3vmax",
  link1Color: "rgba(253, 245, 249, 0.8)",
  nav1justifyContent: "flex-end",
  nav2justifyContent: "flex-end",
  nav3justifyContent: "flex-start",
  nav4justifyContent: "flex-start",
  link1ColorHover: "#eb4034",
  link1Margin: "1vmax",
  profileIconUrl: "/login",
  profileIcon:true,
  profileIconColor:"rgba(253, 245, 249, 0.8)",
  ProfileIconElement:MdAccountCircle,
  searchIcon:true,
  searchIconColor: "rgba(253, 245, 249, 0.8)",
  SearchIconElement:MdSearch,
  cartIcon:true,
  cartIconColor: "rgba(253, 245, 249, 0.8)",
  CartIconElement:MdAddShoppingCart,
  profileIconColorHover: "#eb4034",
  searchIconColorHover: "#eb4034",
  cartIconColorHover: "#eb4034",
  cartIconMargin: "1vmax",
 
};

const Header = () => {
  return (
    <ReactNavbar {...options} />
  )
}

export default Header