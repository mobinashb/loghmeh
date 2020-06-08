import React from 'react';
import logo from '../Assets/LOGO.png';
import {toPersianNum} from '../Utils/Utils';
import PropTypes from "prop-types";

class Navbar extends React.Component {
  showCart() {
    this.props.func("cart");
  }
  render() {
    let profileNavlink = null;
    if (this.props.whereAmI !== "profile") {
      profileNavlink = (<li className="nav-item">
              <a className="nav-link" href="/profile">حساب کاربری</a>
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
            {/* eslint-disable-next-line */}
            <a className="nav-link logout" onClick={this.props.logout}>خروج</a>
          </li>
        </ul>
      </nav>
    </div>
    );
  }
}

Navbar.propTypes = {
  whereAmI: PropTypes.string,
  cartCount: PropTypes.number,
  func: PropTypes.func
};

Navbar.defaultProps = {
  whereAmI: "home",
  cartCount: 0,
  func: null
};

export default Navbar;