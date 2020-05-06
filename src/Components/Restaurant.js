import React from 'react';
import {getQueryParams} from '../Utils/Utils';
import Navbar from './Navbar';
import Modal from "react-bootstrap/Modal";
import CartBasedComponent from './CartBasedComponent';
import FoodDetails from './FoodDetails'
import ClipLoader from 'react-spinners/ClipLoader';
import LoadingOverlay from 'react-loading-overlay';
import Error from '../Error/Error';
import {SERVER_URI} from "../Constants/Constants";

class Restaurant extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      id: null,
      menu: [],
      logo: '',
      name: '',
      cart: {},
      toShow: null,
    };
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
  }

  Menu(menu) {
    let Menu = [];
    let rowContent = [];
    menu.map((item, i) => {
      if ((i % 3 === 0) && i !== 0) {
        Menu.push(<div className="row" key={"row".concat(i)}>{rowContent}</div>);
        rowContent = [];
      }
      rowContent.push(
        <FoodDetails key={item.name}
          whereAmI="menu"
          name={item.name} restaurantName={this.state.name} restaurantId={this.state.id}
          description={item.description}
          price={item.price}
          popularity={item.popularity}
          count={item.count}
          oldPrice={item.oldPrice}
          showFunc={this.handleShow}
          hideFunc={this.handleHide}
          addToCart={this.addToCart}
          image={item.image} />
      );
      return item;
    });
    Menu.push(<div className="row" key={"lastRow"}>{rowContent}</div>);
    return Menu;
  }

  render() {
    const {error,
    isLoaded,
    menu,
    logo,
    name,
    cart,
    toShow} = this.state;
    var cartOrdersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length;
    if (error) {
      if (error.includes("یافت نشد")) return <Error code={404}/>;
      return <Error code={500}/>;
    } else {
      return (
      <LoadingOverlay
        active={!isLoaded}
        spinner={<ClipLoader
          size={40}
          color={"#ff6b6b"}
          loading={!this.state.isLoaded}
        />}>
        <Navbar whereAmI="restaurant" cartCount={cartOrdersLen} func={this.handleShow} logout={this.logout}/>
        <header className="container-fluid banner row-sm-12">
        </header>
        <div className="restaurant-logo centered-flex">
            <figure className="figure">
                <img src={logo} className="figure-img shadow-box" alt={name} />
                <figcaption className="figure-caption">{name}</figcaption>
            </figure>
        </div>
        <div className="row container-fluid" id="restaurant-div">
          <div className="col-sm-4" id="cart">
            <div className="card shadow-box">
            {cartOrdersLen > 0
            ? this.Cart()
            : <h1>سبد خرید شما خالی است</h1>
            }
            </div>
          </div>
          <div className="menu col-sm-8 right-dashed-border container">
            <div className="title">منوی غذا</div>
            {this.Menu(menu)}
          </div>
        </div>
        <Modal className="modal fade" role="dialog"
          show={toShow === "cart"}
          onHide={this.handleHide}>
          <Modal.Body>
            <div id="cart">
              <div className="card">
              {cartOrdersLen > 0
              ? this.Cart()
              : <h1>سبد خرید شما خالی است</h1>
              }
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </LoadingOverlay>
    );
    }
  }

  fetchRestaurant() {
    const jwt = localStorage.getItem("jwt");
    const path = SERVER_URI + "/restaurants/".concat(getQueryParams(this.props.location.search, 'id'));
    fetch(path,
  {
        headers: {
          Authorization: `Bearer ${jwt}`
        }
      })
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            menu: result.menu,
            name: result.name,
            logo: result.logo,
            id: result.id,
            error: (!this.state.error) ? result.msg : this.state.error
          });
        },
        (error) => {
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      );
  }

  componentDidMount() {
    this.fetchRestaurant();
    this.fetchCart();
  }
}

export default Restaurant;