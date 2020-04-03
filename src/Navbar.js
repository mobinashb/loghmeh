import React from 'react';
import logo from './Assets/LOGO.png'
import {toPersianNum} from './Utils'

class Navbar extends React.Component {
  showCart() {
    this.props.func("cart");
  }
  render() {
    let profileNavlink = null;
    if (this.props.whereAmI !== "profile") {
      profileNavlink = (<li class="nav-item">
              <a class="nav-link" href="/profile">حساب کاربری</a>
              </li> );
    }
    return (
      <div>
      <nav className="navbar-expand-sm bg-white fixed-top">
        {this.props.whereAmI !== "home" &&
        <a href="/" className="navbar-brand float-right">
          <img className="logo" src={logo} alt="لقمه" />
        </a>
        }
        <ul className="navbar-nav float-left">
          <li className="nav-item">
            <i className="flaticon-smart-cart" onClick={this.showCart.bind(this)}><span className="count-badge">{toPersianNum(this.props.cartCount)}</span></i>
          </li>
          {profileNavlink}
          <li className="nav-item">
            <a className="nav-link logout" href="/">خروج</a>
          </li>
        </ul>
      </nav>
    </div>
    );
  }
}

export default Navbar;