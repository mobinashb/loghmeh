import React from 'react';
import {POST, PUT, toPersianNum} from '../Utils/Utils';
import swal from 'sweetalert';

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
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
  }

  getSum(orders) {
    return orders.reduce(function(prev, current) {
      return prev + +(current.price * current.number)
    }, 0);
  }

  async changeCart(food, num) {
    const id = this.state.cart.restaurantId;
    const foodNew = {
      foodName: food.foodName,
      number: num,
      restaurantId: id,
      isParty: food.isParty
    };
    let response = PUT(foodNew, 'http://localhost:8080/v1/cart');
    const res = await response;
    const text = await (res).text();
    if (res.ok) {
      var cartNew = JSON.parse(JSON.stringify(this.state.cart));
      this.setState(this.state.cart.orders.reduce( (current, item) => {
        if (item.foodName === food.foodName) {
          item.number = item.number + num;
        }
        current.push( item );
        return current;
      }, [] ) );
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
    else {
      swal({
        title: "خطا",
        text: JSON.parse(text).msg,
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
    let response = POST(food, "http://localhost:8080/v1/cart");
    const res = await response;
    const text = await (res).text();
    if (res.ok) {
      let count = this.getFoodCount(food.foodName);
      if (count !== 0) {
        this.changeCart(food, food.number);
        return;
      }
      this.setState({
        cart: cartNew
      })
    }
    else {
      swal({
        title: "خطا",
        text: JSON.parse(text).msg,
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
  }

  async finalizeOrder() {
    let response = POST("", "http://localhost:8080/v1/cart/finalize");
    const res = await response;
    const text = await (res).text();
    if (res.ok) {
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
    else {
      swal({
        title: "خطا",
        text: JSON.parse(text).msg,
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

  fetchCart() {
    fetch("http://localhost:8080/v1/cart")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            cart: result,
            error: (!this.state.error) ? result.msg : this.state.error
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
          orders: result,
          error: (!this.state.error) ? result.msg : this.state.error
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