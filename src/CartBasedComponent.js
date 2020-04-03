import React from 'react';
import {post, toPersianNum} from './Utils';

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

  changeCart(i, num) {
    let changedItem = null;
    this.setState(state => {
      const list = state.cart.orders.map((item, j) => {
        if (j === i) {
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
    post(changedItem, 'http://localhost:8080/v1/cart');
  }

  getFoodCount(name) {
    let food = this.state.cart.orders.find((element) => {
      return element.name === name;
    });
    return food.count;
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