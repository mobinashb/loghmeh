import React from 'react';
import {post} from './Utils'

class CartBasedComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cart: {},
      toShow: null
    }
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
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
}

export default CartBasedComponent;