import React from 'react';
import {POST, PUT, DELETE, toPersianNum} from './Utils';
import swal from 'sweetalert';

class CartBasedComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: {},
      toShow: null,
      error: null,
      isLoaded: false
    }
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.addToCart = this.addToCart.bind(this);
    this.finalizeOrder = this.finalizeOrder.bind(this);
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
  }

  getSum(orders) {
    let sum = orders.reduce(function(prev, current) {
      return prev + +(current.price * current.number)
    }, 0);
    return sum
  }

  async changeCart(name, num) {
    const id = this.state.cart.restaurantId;
    const food = {
      foodName: name,
      number: num,
      restaurantId: id,
    };
    let response = PUT(food, 'http://localhost:8080/v1/cart');
    const res = await response;
    if (res.ok) {
      var cartNew = JSON.parse(JSON.stringify(this.state.cart));
      if (this.getFoodCount(name) === 0 && num === -1) return;
      this.setState(this.state.cart.orders.reduce( (current, item) => {
        if (item.foodName === name) {
          item.number = item.number + num;
        }
        current.push( item );
        return current;
      }, [] ) );
      if (this.getFoodCount(name) === 0) {
        const orders = this.state.cart.orders.filter(item => item.foodName !== name);
        cartNew.orders = orders;
        if (this.state.cart.orders.length === 0) {
          cartNew.restaurantId = null;
          cartNew.name = null;
          // DELETE("", "http://localhost:8080/v1/cart");
        }
        this.setState({
          cart: cartNew
        });
      }
    }

  }

  getFoodCount(name) {
    let food = this.state.cart.orders.find((element) => {
      return element.foodName === name;
    });
    if (food === null || food === undefined) return 0;
    return food.number;
  }

  async addToCart(food) {
    const curRestaurant = this.state.cart.restaurantId;
    var cartNew = JSON.parse(JSON.stringify(this.state.cart));
    cartNew.orders = cartNew.orders.concat(food);
    if ((curRestaurant !== null) && (curRestaurant !== food.restaurantId)) {
      swal({
        title: "خطا",
        text: "شما قبلا از رستوران دیگری سفارش داده اید!",
        icon: "warning",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      })
      return;
    }
    let response = POST(food, "http://localhost:8080/v1/cart");
    const res = await response;
    if (res.ok) {
      let count = this.getFoodCount(food.foodName);
      if (count !== 0) {
        this.changeCart(food.foodName, food.number);
        return;
      }
      this.setState({
        cart: cartNew
      })
    }
  }

  async finalizeOrder() {
    let response = POST("", "http://localhost:8080/v1/cart/finalize");
    const res = await response;
    if (res.ok) {
      this.setState({
        cart: {
          restaurantId: null,
          name: null
        }
      })
      if (this.constructor.name === "Profile") {
        this.fetchOrders();
      }
    }
    else {
      swal({
        title: "خطا",
        text: "اعتبار شما کافی نیست!",
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
              <span className="float-right">
              {food.foodName}
              </span>
              <span className="plus-minus float-left">
                <i className="flaticon-minus" onClick={this.changeCart.bind(this, food.foodName, -1)}></i>
                &nbsp;&nbsp;
                {toPersianNum(this.getFoodCount(food.foodName))}
                <i className="flaticon-plus" onClick={this.changeCart.bind(this, food.foodName, +1)}></i>
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

  fetchCart() {
    fetch("http://localhost:8080/v1/cart")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            cart: result
          });
        },
        (error) => {
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      )
  }

  fetchOrders() {
    fetch("http://localhost:8080/v1/orders/")
    .then(res => res.json())
    .then(
      (result) => {
        this.setState({
          orders: result
        });
      },
      (error) => {
        this.setState({
          isLoaded: true,
          error: error
        });
      }
    )
  }
}

export default CartBasedComponent;