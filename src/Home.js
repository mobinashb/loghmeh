import React from 'react';
import {Header, toPersianNum} from './Utils';
import FoodDetails from './FoodDetails';
import CartBasedComponent from './CartBasedComponent';
import Navbar from './Navbar';
import {Link} from 'react-router-dom';
import Modal from "react-bootstrap/Modal";
import ClipLoader from 'react-spinners/ClipLoader';
import LoadingOverlay from 'react-loading-overlay';
import Error from './Error';
import Timer from 'react-compound-timer';

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
    return item;
  })
  restaurantList.push(<div className="row" key="lastRow">{rowContent}</div>);
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
      restaurantsInParty: [],
      partyRemainingTime: 0,
      cart: {}
    };
  }
  render() {
    const { error, isLoaded, restaurants, restaurantsInParty, partyRemainingTime, toShow, cart} = this.state;
    let cartOrdersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length
    var foodPartyList = [];
    restaurantsInParty.map((restaurant) => {
      restaurant.menu.map((food) => {
        food.restaurantName = restaurant.name;
        foodPartyList.push(food)
        return food;
      })
      return restaurant;
    });
    if (error) {
      return <Error msg={error}/>
    } else {
      return (
        <LoadingOverlay
        active={!isLoaded}
        spinner={<ClipLoader
          size={40}
          color={"#ff6b6b"}
          loading={!this.state.isLoaded}
        />}>
        <Navbar whereAmI="home" cartCount={cartOrdersLen} func={this.handleShow}/>
        <Header/>
        <Search/>
        <div className="menu">
          <div className="title">
            جشن غذا!
          </div>
          <div className="centered-flex">
            <div className="timer">
              {isLoaded &&<Timer
              initialTime={parseInt(partyRemainingTime)*1000}
              direction="backward"
              >
                زمان باقی مانده: &nbsp;<b><Timer.Minutes formatValue={value => toPersianNum(`${value}`)}/>:<Timer.Seconds formatValue={value => toPersianNum(`${value}`)}/></b>
              </Timer>
              }
              </div>
          </div>
          <div className="scrolling-wrapper shadow-box">
            {foodPartyList.map(item => (
              <div className="card shadow-box" key={item.restaurantId+'-'+item.name}>
                <FoodDetails whereAmI="foodparty"
                name={item.name} restaurantName={item.restaurantName} restaurantId={item.restaurantId}
                description={item.description}
                price={item.price}
                popularity={item.popularity}
                count={item.count}
                oldPrice={item.oldPrice}
                showFunc={this.handleShow}
                hideFunc={this.handleHide}
                addToCart={this.addToCart}
                image={item.image} />
              </div>
          ))}
          </div>
        </div>
        <div className="menu container" id="restaurants">
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
              {cartOrdersLen > 0
              ? this.Cart(cart)
              :
                <h1>سبد خرید شما خالی است</h1>
              }
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </LoadingOverlay>
    );
    }
  }

  fetchFoodParty() {
    fetch("http://localhost:8080/v1/foodparty")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            restaurantsInParty: result
          });
        },
        (error) => {
          this.setState({
            error: error,
            isLoaded: true,
          });
        }
      )
  }

  fetchRestaurants() {
    fetch("http://localhost:8080/v1/restaurants")
      .then(res => res.json())
      .then(
        (result) => {
          console.log(result)
          this.setState({
            restaurants: result,
          });
        },
        (error) => {
          this.setState({
            error: error,
            isLoaded: true,
          });
        }
      )
  }

  fetchRemainingTime() {
    fetch("http://localhost:8080/v1/foodparty/remainingTime")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            partyRemainingTime: result.remainingTime,
            isLoaded: true,
            error: result.msg
          });
        },
        (error) => {
          this.setState({
            error: error,
            isLoaded: true,
          });
        }
      )
  }

  componentDidMount() {
    this.fetchCart();
    this.fetchRestaurants();
    this.fetchFoodParty();
    this.fetchRemainingTime();
  }
}

export default Home;
