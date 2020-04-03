import React from 'react';
import {toPersianNum, post, getQueryParams} from './Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar'
import Modal from "react-bootstrap/Modal";
import CartBasedComponent from './CartBasedComponent';
import star from './Assets/star.png';

function Menu(props) {
  let Menu = [];
  let rowContent = [];
  props.menu.map((item, i) => {
    if ((i % 3 === 0) && i !== 0) {
      Menu.push(<div className="row" key={"row".concat(i)}>{rowContent}</div>);
      rowContent = [];
    }
    rowContent.push(
      <FoodCard item={item} key={item.id}/>
    )
  })
  Menu.push(<div className="row">{rowContent}</div>);
  return Menu;
}

function FoodCard(params) {
  return (
    <div class="col-sm-4">
      <div class="card shadow-box">
        <div class="card-body">
          <img src={params.item.image} alt={params.item.name}/>
          <div class="row">
            <div class="food-title">
              {params.item.name}
            </div>
            <span class="rating inline">
            {toPersianNum(params.item.popularity * 5)}
              <img src={star} alt=""/>
            </span>
          </div>
          <h6>{toPersianNum(params.item.price)} تومان</h6>
        </div>
        <button class="small-btn yellow-btn">افزودن به سبد خرید</button>
      </div>
    </div>
  );
}


class Restaurant extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      id: '',
      menu: [],
      logo: '',
      name: '',
      cart: {},
      toShow: null
    };
  }

  render() {
    const {error,
    isLoaded,
    id,
    menu,
    logo,
    name,
    cart,
    toShow} = this.state;
    var cartOrdersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length
    return (
      <div>
        <Navbar whereAmI="restaurant" cartCount={0} func={this.handleShow}/>
        <header class="container-fluid banner row-sm-12">
        </header>
        <div class="restaurant-logo centered-flex">
            <figure class="figure">
                <img src={logo} class="figure-img shadow-box" alt={name} />
                <figcaption class="figure-caption">{name}</figcaption>
            </figure>
        </div>
        <div class="row container-fluid" id="restaurant-div">
          <div class="col-sm-3" id="cart">
            <div className="card shadow-box">
            {cartOrdersLen > 0 &&
            this.Cart
            }
            {
              <h1>سبد خرید شما خالی است</h1>
            }
            </div>
          </div>
          <div className="menu col-sm-9 right-dashed-border">
            <div class="title">منوی غذا</div>
            <Menu menu={menu} />
          </div>
        </div>
        <Modal className="modal fade" role="dialog"
          show={toShow === "cart"}
          onHide={this.handleHide}>
          <Modal.Body>
            <div id="cart">
              <div className="card">
              {cartOrdersLen > 0 &&
              this.Cart
              }
              {
                <h1>سبد خرید شما خالی است</h1>
              }
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </div>
    );
  }

  componentDidMount() {
    fetch("http://localhost:8080/v1/restaurants/".concat(getQueryParams(this.props.location.search, 'id')))
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            menu: result.menu,
            name: result.name,
            logo: result.logo
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      )
      fetch("http://localhost:8080/v1/cart")
      .then(res => res.json())
      .then(
        (result) => {
          console.log(result);
          this.setState({
            isLoaded: true,
            cart: result
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      )

  }
}

export default Restaurant;