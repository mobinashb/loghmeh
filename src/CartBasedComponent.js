import React from 'react';
import {post, toPersianNum} from './Utils';
import swal from 'sweetalert';

class CartBasedComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: {},
      toShow: null
    }
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.changeCart = this.changeCart.bind(this);
    this.addToCart = this.addToCart.bind(this);
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
  }

  getSum(orders) {
    let sum = orders.reduce(function(prev, current) {
      return prev + +(current.price * current.count)
    }, 0);
    return sum
  }

  changeCart(name, num) {
    let changedItem = null;
    this.setState(state => {
      const list = state.cart.orders.map((item) => {
        if (item.name === name) {
          if (item.count === 0 && num === -1) return item.count;
          changedItem = item;
          changedItem.count = item.count + num;
          return changedItem
        } else {
          return item;
        }
      });
      return {
        list,
      };
    });
    console.log(changedItem);
    var food = {
      foodName: changedItem.name,
      number: num,
      restaurantId: this.state.cart.restaurantId,
      // isParty: (changedItem.oldPrice !== undefined) ? 1 : 0
    }
    post(food, 'http://localhost:8080/v1/cart');
  }

  getFoodCount(name) {
    let food = this.state.cart.orders.find((element) => {
      return element.name === name;
    });
    return food.count;
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
  }

  Cart(props) {
    return (
    <div>
      <div className="title">
          سبد خرید
      </div>
      <div className="card-body">
        <div className="dashed-div">
          {props.cart.orders.map((food, i) => (
          <div key={food.name}>
              {food.name}
              <span className="plus-minus">
                <i className="flaticon-minus" onClick={this.changeCart(i, -1)}></i>
                &nbsp;&nbsp;
                {toPersianNum(this.getFoodCount(food.name))}
                <i className="flaticon-plus" onClick={this.changeCart(i, +1)}></i>
              </span>
              <p className="price">{toPersianNum(food.price * food.count)} تومان</p>
          </div>
        ))}
        </div>
        <div className="sum">
            جمع کل:
            <p className="bold">{toPersianNum(this.getSum(props.cart.orders))} تومان</p>
        </div>
      </div>
      <button className="btn cyan-btn">تأیید نهایی</button>
    </div>
    );
  }
}

export default CartBasedComponent;