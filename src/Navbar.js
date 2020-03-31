import React from 'react';
// import './CSS/styles.css';
// import 'bootstrap/dist/css/bootstrap.css';
import logo from './Assets/LOGO.png'
import {toPersianNum} from './Utils'

class Navbar extends React.Component {
  render() {
    let profileNavlink = null;
    if (this.props.inProfile === "false") {
      profileNavlink = (<li class="nav-item">
              <a class="nav-link" href="/profile">حساب کاربری</a>
              </li> );
    }
    return (
      <div>
      <nav class="navbar-expand-sm bg-white fixed-top">
        <a href="/" class="navbar-brand float-right">
          <img class="logo" src={logo} alt="لقمه" />
        </a>
        <ul class="navbar-nav float-left">
          <li class="nav-item">
            <a href="cart.js">
              <i class="flaticon-smart-cart"><span class="count-badge">{toPersianNum(this.props.cartCount)}</span></i>
            </a>
          </li>
          {profileNavlink}
          <li class="nav-item">
            <a class="nav-link logout" href="/">خروج</a>
          </li>
        </ul>
      </nav>
    </div>
    );
  }
}

export default Navbar;