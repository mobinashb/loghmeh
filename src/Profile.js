import React from 'react';
import {toPersianNum, post} from './Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar'
import Modal from "react-bootstrap/Modal";

function Banner(props) {
  let name = props.firstname + ' ' + props.lastname;
  let email = props.email;
  let phonenumber = props.phonenumber;
  let credit = props.credit;
  return (
    <header className="container-fluid banner row-sm-12">
      <div className="col-sm-6">
          <i className="flaticon-account"></i>
          {name}
      </div>
      <div className="col-sm-6">
          <ul>
          <li>
              <i dir="ltr" className="flaticon-phone"></i>
              {phonenumber}
          </li>
          <li>
              <i dir="ltr" className="flaticon-mail"></i>
              {email}
          </li>
          <li>
              <i dir="ltr" className="flaticon-card"></i>
              {toPersianNum(credit)} &nbsp;
              تومان
          </li>
          </ul>
      </div>
    </header>
  );
}

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: props.firstname,
      lastname: props.lastname,
      email: props.email,
      phonenumber: props.phonenumber,
      credit: props.credit,
      orders: props.orders,
      toShow: null,
      cart: props.cart
    }
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
  }

  handleShow(id) {
    this.setState({toShow: id});
  }

  handleHide() {
    this.setState({toShow: null});
  }

  changeCart(i, num) {
    this.setState(state => {
      const list = state.cart.foodList.map((item, j) => {
        if (j === i) {
          if (item.count === 0 && num === -1) return item.count;
          let changedItem = item;
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
    post(this.state.cart, 'http://localhost:8080/v1/cart');
  }

  getFoodCount(name) {
    let food = this.state.cart.foodList.find((element) => {
      return element.name === name;
    });
    return food.count;
  }

  getSum(foodList) {
    let sum = foodList.reduce(function(prev, current) {
      return prev + +(current.price * current.count)
    }, 0);
    return sum
  }

  render() {
    const {firstname, lastname, email, phonenumber, credit, orders, toShow, cart} = this.state;

    return (
      <div>
        <Navbar inProfile="true" cartCount={cart.foodList.length} func={this.handleShow}/>
        <Banner firstname={firstname} lastname={lastname} email={email} phonenumber={phonenumber} credit={credit}/>
        <div className="warpper">
          <input className="radio" id="one" name="group" type="radio" defaultChecked={true} />
          <input className="radio" id="two" name="group" type="radio" />
          <div className="tabs">
          <label className="tab" id="one-tab" htmlFor="one">سفارش ها</label>
          <label className="tab" id="two-tab" htmlFor="two">افزایش اعتبار</label>
            </div>
          <div className="panels">
            <div className="panel row-sm-5" id="one-panel">
              {orders.map((order, i) => (
                <div className="row" key={order.id}>
                  <div className="col-1 col-bordered bg-light">
                    {toPersianNum(i+1)}
                  </div>
                  <div className="col-7 col-bordered bg-light">
                    {order.restaurantName}
                  </div>
                  <div className="col-4 col-bordered bg-light">
                    {order.status === "delivering" &&
                      <button disabled className="green-btn small-btn">پیک در مسیر</button>
                    }
                    {order.status === "finding delivery" &&
                      <button disabled className="blue-btn small-btn">در جست‌و‌جوی پیک</button>
                    }
                    {order.status === "delivered" &&
                      <button className="yellow-btn small-btn" onClick={() => this.handleShow(order.id)}>مشاهده فاکتور</button>
                    }
                  </div>
                  <Modal className="modal fade" role="dialog"
                   show={toShow === order.id}
                   onHide={this.handleHide}>
                    <Modal.Body>
                      <h2>
                        {order.restaurantName}
                      </h2>
                      <hr className="thin"></hr>
                      <div className="table-responsive bg-white">
                        <table className="table table-bordered table-small">
                          <thead className="thead-light">
                            <tr>
                              <th scope="col" className="text-center">ردیف</th>
                              <th scope="col" className="text-center">نام غذا</th>
                              <th scope="col" className="text-center">تعداد</th>
                              <th scope="col" className="text-center">قیمت</th>
                            </tr>
                          </thead>
                          <tbody>
                          {order.foodList.map((food, i) => (
                            <tr key={food.name}>
                              <th scope="row" className="text-center">{toPersianNum(i+1)}</th>
                              <td className="text-center">{food.name}</td>
                              <td className="text-center">{toPersianNum(food.count)}</td>
                              <td className="text-center">{toPersianNum(food.price)}</td>
                            </tr>
                          ))}
                          </tbody>
                        </table>
                        <p className="bold">
                          جمع کل: {toPersianNum(this.getSum(order.foodList))} تومان
                        </p>
                      </div>
                    </Modal.Body>
                  </Modal>
                </div>
              ))}
            </div>
            <div className="panel row-sm-5" id="two-panel">
              <form className="form-inline justify-content-center" action="addcredit" id="credit" method="POST">
                <div className="form-group">
                  <input type="text" className="form-control bg-light" placeholder="میزان افزایش اعتبار" />
                </div>
                <button type="submit" className="btn cyan-btn">افزایش</button>
              </form>
            </div>
          </div>
        </div>
        <Modal className="modal fade" role="dialog"
          show={toShow === "cart"}
          onHide={this.handleHide}>
          <Modal.Body>
            <div id="cart">
              <div className="card">
                <div className="title">
                    سبد خرید
                </div>
                <div className="card-body">
                  <div className="dashed-div">
                  {cart.foodList.map((food, i) => (
                    <div key={food.name}>
                        {food.name}
                        <span className="plus-minus">
                          <i className="flaticon-minus" onClick={this.changeCart.bind(this, i, -1)}></i>
                          &nbsp;&nbsp;
                          {toPersianNum(this.getFoodCount(food.name))}
                          <i className="flaticon-plus" onClick={this.changeCart.bind(this, i, +1)}></i>
                        </span>
                        <p className="price">{toPersianNum(food.price * food.count)} تومان</p>
                    </div>
                  ))}
                  </div>
                  <div className="sum">
                      جمع کل:
                      <p className="bold">{toPersianNum(this.getSum(cart.foodList))} تومان</p>
                  </div>
                </div>
                <button className="btn cyan-btn">تأیید نهایی</button>
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

Profile.propTypes = {
  firstname: PropTypes.string,
  lastname: PropTypes.string,
  email: PropTypes.string,
  phonenumber: PropTypes.string,
  credit: PropTypes.number,
  orders: PropTypes.array,
  cart: PropTypes.object
}

export default Profile;
