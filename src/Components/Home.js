import React from 'react';
import {Header, toQueryParams} from '../Utils/Utils';
import CartBasedComponent from './CartBasedComponent';
import Navbar from './Navbar';
import {Link} from 'react-router-dom';
import Modal from "react-bootstrap/Modal";
import ClipLoader from 'react-spinners/ClipLoader';
import LoadingOverlay from 'react-loading-overlay';
import Error from '../Error/Error';
import InfiniteScroll from 'react-infinite-scroller';
import SearchForm from '../Forms/SearchForm';
import swal from "sweetalert";
import {SERVER_URI} from "../Constants/Constants";
import axios from 'axios';
import FoodParty from './FoodParty';

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
    );
    return item;
  });
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
      cart: {},
      pageNum: 1,
      pageSize: 16,
      hasMore: true,
      api: SERVER_URI + "/restaurants?"
    };
  }
  render() {
    const { error, isLoaded, restaurants, toShow, cart} = this.state;
    let cartOrdersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length;
    if (error) {
      return <Error code={500}/>;
    }
    else {
      return (
        <LoadingOverlay
        active={!isLoaded}
        spinner={<ClipLoader
          size={40}
          color={"#ff6b6b"}
          loading={!isLoaded}
        />}>
        <Navbar whereAmI="home" cartCount={cartOrdersLen} func={this.handleShow} logout={this.logout}/>
        <Header/>
        <SearchForm updateRestaurants={this.updateRestaurants.bind(this)}/>
        <FoodParty setLoaded={this.setLoaded.bind(this)}/>
        <div className="menu container" id="restaurants">
          <div className="title">
            رستوران ها
          </div>
          {isLoaded &&
          <InfiniteScroll
              pageStart={1}
              loadMore={this.loadMoreRestaurants.bind(this)}
              hasMore={this.state.hasMore}
              loader={<div className="loader" key={0}>در حال بارگذاری ...</div>}
          >
            <RestaurantList restaurants={restaurants}/>
          </InfiniteScroll>
          }
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

  loadMoreRestaurants() {
    const pageNum = this.state.pageNum;
    const pageSize = this.state.pageSize;
    const params = toQueryParams({
      pageNum: pageNum,
      pageSize: pageSize
    });
    const path = this.state.api + params;
    setTimeout( () => {
      this.fetchRestaurants(path);
    }, 1000);
  }

  updateRestaurants(searchQuery) {
    const searchQueryString = toQueryParams(searchQuery);
    const api = SERVER_URI + "/search?";
    this.setState({
      restaurants: [],
      pageNum: 1,
      api: api + searchQueryString + "&",
      hasMore: true
    });
  }

  fetchRestaurants(path) {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    if (this.state.hasMore === false) return;
    axios.get(path, options)
        .then((response) => {
          if (response.data.length > 0) {
            this.setState({
              restaurants: this.state.restaurants.concat(response.data),
              error: (!this.state.error) ? response.data.msg : this.state.error,
              hasMore: true,
              pageNum: this.state.pageNum + 1
            });
          }
          else {
            this.setState({
              hasMore: false
            });
            if (typeof this.state.restaurants === "undefined" ||
                this.state.restaurants === null ||
                this.state.restaurants.length === null ||
                this.state.restaurants.length === 0) {
              swal({
                title: "هشدار",
                text: "رستورانی مطابق با جستجوی شما یافت نشد!",
                icon: "warning",
                dangerMode: true,
                button: {
                  text: "بستن",
                  value: null,
                  visible: true,
                  closeModal: true,
                },
              });
            }
          }
        },
        (error) => {
          this.handleError(error);
        }
      );
  }

  setLoaded() {
    this.setState({
      isLoaded: true
    });
  }

  componentDidMount() {
    const pageNum = this.state.pageNum;
    const pageSize = this.state.pageSize;
    const params = toQueryParams({
      pageNum: pageNum,
      pageSize: pageSize
    });
    const path = this.state.api + params;
    this.fetchRestaurants(path)
    // this.fetchFoodParty();
    this.fetchCart();
  }
}

export default Home;
