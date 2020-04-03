import React from 'react';
import {Header, toPersianTime} from './Utils'
import FoodDetails from './FoodDetails'
import CartBasedComponent from './CartBasedComponent';
import Navbar from './Navbar';
import {Link} from 'react-router-dom';
import Modal from "react-bootstrap/Modal";

function Search() {
  return (
    <div className="search centered-flex">
      <form className="form-inline justify-content-center shadow-box" action="search">
        <div className="form-group">
          <input type="text" className="form-control bg-light" id="foodname" placeholder="نـــام غـــذا" />
          <input type="text" className="form-control bg-light" id="restaurantname" placeholder="نـــام رســـتـــوران" />
        </div>
        <button type="submit" className="btn btn-default">جســـت‌و‌جـــو</button>
      </form>
    </div>
  );
}

function RestaurantList(props) {
  let restaurantList = [];
  let rowContent = [];
  props.restaurants.map((item, i) => {
    if ((i % 4 === 0) && i !== 0) {
      restaurantList.push(<div className="row" key={"row".concat(i)}>{rowContent}</div>);
      rowContent = [];
    }
    rowContent.push(
      <RestaurantCard item={item} key={item.id}/>
    )
  })
  restaurantList.push(<div className="row">{rowContent}</div>);
  return restaurantList;
}

function RestaurantCard(params) {
  var url = "restaurant?id=".concat(params.item.id);
  return (
    <div className="col-sm-3">
        <div className="card shadow-box">
          <div className="card-body">
            <img src={params.item.logo} alt={params.item.name}/>
            <div className="restaurant-title">
              {params.item.name}
            </div>
          </div>
          <Link to={url} className="btn yellow-btn">نمایش منو</Link>
        </div>
      </div>
  )
}

class Home extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      restaurants: [],
      partyRestaurants: [],
      timeLeft: 0,
      cart: []
    };
  }
  render() {
    const { error, isLoaded, restaurants, partyRestaurants, timeLeft, toShow, cart} = this.state;
    let cartOrdersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length
    var foodPartyList = [];
    partyRestaurants.map((restaurant) => {
      restaurant.menu.map((food) => {
        foodPartyList.push(food)
      })
    });
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
      <div>
        <Navbar whereAmI="home" cartCount={cartOrdersLen} func={this.handleShow}/>
        <Header/>
        <Search/>
        <div className="menu">
          <div className="title">
            جشن غذا!
          </div>
          <div className="centered-flex">
            <div className="timer">
              زمان باقی مانده: {toPersianTime(timeLeft)}
            </div>
          </div>
          <div className="scrolling-wrapper shadow-box">
            {foodPartyList.map(item => (
              <div key={item.restaurantId+'-'+item.name}>
                <FoodDetails expand="false" />
                <FoodDetails expand="true" name={item.name} restaurantName={item.restaurantName} restaurantId={item.restaurantId}
                description={item.description}
                price={item.price}
                rating={item.rating}
                count={item.count}
                oldPrice={item.oldPrice}
                img={item.img}
                />
              </div>
          ))}
          </div>
        </div>
        <div className="menu container">
          <div className="title">
            رستوران ها
          </div>
          <RestaurantList restaurants={restaurants}/>
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
  }

  componentDidMount() {
    fetch("http://localhost:8080/v1/restaurants")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            restaurants: result
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
      fetch("http://localhost:8080/v1/partyRestaurants")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            partyRestaurants: result
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

export default Home;
