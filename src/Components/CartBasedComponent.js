import React from 'react';
import {toPersianNum} from '../Utils/Utils';
import swal from 'sweetalert';
import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";

class CartBasedComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: {},
      toShow: null,
      error: null,
      isLoaded: false
    };
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.addToCart = this.addToCart.bind(this);
    this.finalizeOrder = this.finalizeOrder.bind(this);
    this.logout = this.logout.bind(this);
    window.addEventListener('storage', this.logout);
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
  }

  getSum(orders) {
    return orders.reduce(function(prev, current) {
      return prev +(current.price * current.number)
    }, 0);
  }

  handleError(error) {
    if (error.response.status === 401 || error.response.status === 403) {
      this.props.history.push('/login');
      return;
    }

    this.setState({
      error: error.message,
      isLoaded: true,
    });

    swal({
      title: "خطا",
      text: error.response.data.msg,
      icon: "warning",
      dangerMode: true,
      button: {
        text: "بستن",
        value: null,
        visible: true,
        closeModal: true,
      },
    })
  }

  changeCart(food, num) {
    const id = this.state.cart.restaurantId;
    const foodNew = {
      foodName: food.foodName,
      number: num,
      restaurantId: id,
      isParty: food.isParty
    };
    const jwt = localStorage.getItem("jwt");
    let options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.put(SERVER_URI + '/cart', foodNew, options)
      .then((response) => {
        if (response.status === 200) {
          var cartNew = JSON.parse(JSON.stringify(this.state.cart));
          this.setState(this.state.cart.orders.reduce((current, item) => {
            if (item.foodName === food.foodName) {
              item.number = item.number + num;
            }
            current.push(item);
            return current;
          }, []));
          if (this.getFoodCount(food.foodName) === 0) {
            cartNew.orders = this.state.cart.orders.slice(0).filter(item => item.foodName !== food.foodName);
            if (this.state.cart.orders.length === 0) {
              cartNew.restaurantId = null;
              cartNew.name = null;
            }
            this.setState({
              cart: cartNew
            });
          }
        }
      }, (error) => {
        this.handleError(error);
      });

  }

  getFoodCount(name) {
    if (this.state.cart.orders === undefined || this.state.cart.orders === null)
      return 0;
    let food = this.state.cart.orders.find((element) => {
      return element.foodName === name;
    });
    if (food === null || food === undefined) return 0;
    return food.number;
  }

  async addToCart(food) {
    var cartNew = JSON.parse(JSON.stringify(this.state.cart));
    cartNew.restaurantId = food.restaurantId;
    if (cartNew.orders === undefined || cartNew.orders === null) {
      cartNew.orders = []
    }
    cartNew.orders = cartNew.orders.concat(food);
    const jwt = localStorage.getItem("jwt");
    let options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.post(SERVER_URI + "/cart", food, options)
      .then((response) => {
        if (response.status === 200) {
          let count = this.getFoodCount(food.foodName);
          if (count !== 0) {
            this.changeCart(food, food.number);
            return;
          }
          this.setState({
            cart: cartNew
          })
        }
      }, (error) => {
        this.handleError(error);
      });
  }

  finalizeOrder() {
    const jwt = localStorage.getItem("jwt");
    let options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.post(SERVER_URI + "/cart/finalize", "", options)
      .then((response) => {
        if (response.status === 200) {
          this.setState({
            cart: {
              restaurantId: null,
              name: null
            }
          });
          swal({
            text: "سفارش شما با موفقیت ثبت شد",
            icon: "success",
            button: {
              text: "بستن",
              value: null,
              visible: true,
              closeModal: true,
            },
          });
          if (this.constructor.name === "Profile") {
            this.fetchOrders();
          }
        }
      }, (error) => {
        this.handleError(error);
        this.fetchCart();
      }
    );
    this.handleHide();
  }

  Cart() {
    const cart = this.state.cart;
    return (
    <div className="container">
      <div className="title">
          سبد خرید
      </div>
      <div >
        <div className="dashed-div">
          {cart.orders.map((food) => (
          <div key={food.foodName} className="bottom-bordered flexcontainer">
            <div className="row">
              <span className="col-sm-7">
              {food.foodName}
              </span>
              <span className="plus-minus col-sm-5">
                <i className="flaticon-minus" onClick={this.changeCart.bind(this, food, -1)}/>
                &nbsp;&nbsp;
                {toPersianNum(this.getFoodCount(food.foodName))}
                <i className="flaticon-plus" onClick={this.changeCart.bind(this, food, +1)}/>
              </span>
            </div>
            <p className="price float-left">{toPersianNum(food.price * this.getFoodCount(food.foodName))} تومان</p>
          </div>
        ))}
        </div>
        <div className="sum">
            جمع کل:
            <p className="bold">{toPersianNum(this.getSum(cart.orders))} تومان</p>
        </div>
      </div>
      <button className="btn cyan-btn" onClick={this.finalizeOrder}>تأیید نهایی</button>
    </div>
    );
  }

  logout() {
    localStorage.removeItem("jwt");
    const gauth = window.gapi;
    if (gauth !== undefined) {
      const auth2 = gauth.auth2.getAuthInstance();
      if (auth2 != null) {
        auth2.signOut().then(
            auth2.disconnect()
        )
      }
    }
    this.props.history.push('/login');
  }

  fetchCart() {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.get(SERVER_URI + "/cart", options)
        .then(
            (response) => {
            this.setState({
              cart: response.data,
              error: (!this.state.error) ? response.data.msg : this.state.error
            });
        },
        (error) => {
          if (error.response.status === 401 || error.response.status === 403) {
            this.props.history.push('/login');
            return;
          }
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      )
  }

  fetchOrders() {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.get(SERVER_URI + "/orders", options)
    .then(
      (response) => {
        this.setState({
          orders: response.data,
          error: (!this.state.error) ? response.data.msg : this.state.error
        });
      },
      (error) => {
        this.handleError(error);
        this.setState({
          isLoaded: true,
          error: error
        });
      }
    )
  }
}

export default CartBasedComponent;