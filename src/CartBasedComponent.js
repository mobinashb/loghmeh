import React from 'react';
import {post, put, toPersianNum} from './Utils';
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
    // this.changeCart = this.changeCart.bind(this);
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
    if (this.getFoodCount(name) === 0 && num === -1) return;
    // this.setState(this.state.cart.orders.reduce( (current, item) => {
    //   if (item.foodName === name) {
    //     item.number = item.number + num;
    //   }
    //   current.push( item );
    //   return current;
    // }, [] ) );
    // if (this.getFoodCount(name) === 0) {
    //   const orders = this.state.cart.orders.filter(item => item.foodName !== name);
    //   this.setState({
    //     cart: {
    //       orders: orders
    //     }});
    // }
    // if (this.state.cart.orders.length === 0) {
    //   this.setState({
    //     cart: {
    //       restaurantId : null,
    //       name: null
    //     }});
    // }
    var food = {
      foodName: name,
      number: num,
      restaurantId: this.state.cart.restaurantId,
    };
    put(food, 'http://localhost:8080/v1/cart');
    while (!this.state.isLoaded);
    this.fetchCart();
  }

  getFoodCount(name) {
    let food = this.state.cart.orders.find((element) => {
      return element.foodName === name;
    });
    return food.number;
  }

  addToCart(food) {
    var curRestaurant = this.state.cart.restaurantId;
    if (curRestaurant !== null && curRestaurant !== food.restaurantId) {
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
    post(food, "http://localhost:8080/v1/cart");
    this.fetchCart();
  }

  finalizeOrder() {
    post("", "http://localhost:8080/v1/finalize");
    this.fetchCart();
  }

  Cart(cart) {
    return (
    <div>
      <div className="title">
          سبد خرید
      </div>
      <div >
        <div className="dashed-div">
          {cart.orders.map((food) => (
          <div key={food.foodName} className="bottom-bordered">
            {food.foodName}
            <span className="plus-minus float-left">
              <i className="flaticon-minus" onClick={this.changeCart.bind(this, food.foodName, -1)}></i>
              &nbsp;&nbsp;
              {toPersianNum(this.getFoodCount(food.foodName))}
              <i className="flaticon-plus" onClick={this.changeCart.bind(this, food.foodName, +1)}></i>
            </span>
            <div className="price">{toPersianNum(food.price * this.getFoodCount(food.foodName))} تومان</div>
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
}

export default CartBasedComponent;