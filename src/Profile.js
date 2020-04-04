import React from 'react';
import {toPersianNum} from './Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar'
import Modal from "react-bootstrap/Modal";
import CreditForm from './CreditForm'
import CartBasedComponent from './CartBasedComponent'

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

class Profile extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      firstname: '',
      lastname: '',
      email: '',
      phonenumber: '',
      credit: 0,
      orders: [],
      toShow: null,
      cart: {},
      error: null,
      isLoaded: false
    }
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.updateCredit = this.updateCredit.bind(this);
  }

  OrderList() {
    return (
      this.state.orders.map((order, i) => (
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
           show={this.state.toShow === order.id}
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
                  {order.orders.map((food, i) => (
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
                  جمع کل: {toPersianNum(this.getSum(order.orders))} تومان
                </p>
              </div>
            </Modal.Body>
          </Modal>
        </div>
      ))
    );
  }

  updateCredit(amount) {
    const cr = this.state.credit;
    this.setState({credit: parseInt(cr) + parseInt(amount)});
  }

  render() {
    const {firstname,
    lastname,
    email,
    phonenumber,
    credit,
    orders,
    toShow,
    cart,
    error,
    isLoaded} = this.state;
    let cartOrdersLen = 0;
    let ordersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length
    if (orders !== undefined && orders !== null && orders.length > 0)
      ordersLen = orders.length
    if (error) {
      return <div>Error: {error.message}</div>;
    } else {
      // return <div>{cartOrdersLen}</div>;
    return (
      <div>
        <Navbar whereAmI="profile" cartCount={cartOrdersLen} func={this.handleShow}/>
        <Banner firstname={firstname} lastname={lastname} email={email} phonenumber={toPersianNum(phonenumber)} credit={credit}/>
        <div className="warpper">
          <input className="radio" id="one" name="group" type="radio" defaultChecked={true}/>
          <input className="radio" id="two" name="group" type="radio"/>
          <div className="tabs">
            <label className="tab" id="one-tab" htmlFor="one">سفارش ها</label>
            <label className="tab" id="two-tab" htmlFor="two">افزایش اعتبار</label>
          </div>
          <div className="panels">
              <div className="panel row-sm-5" id="one-panel">
              {ordersLen > 0 &&
              this.OrderList}
              {
                <h1>سفارشی ثبت نشده است</h1>
              }
              </div>
              <div className="panel row-sm-5" id="two-panel">
              <CreditForm update={this.updateCredit} />
              </div>
          </div>
        </div>
        <Modal className="modal fade" role="dialog"
          show={toShow === "cart"}
          onHide={this.handleHide}>
          <Modal.Body>
            <div id="cart">
              <div className="card">
              {cartOrdersLen > 0 &&
              this.Cart
              }
              {
                <h1>سبد خرید شما خالی است</h1>
              }
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </div>
    );
    }
  }

  componentDidMount() {
    fetch("http://localhost:8080/v1/profile")
    .then(res => res.json())
    .then(
      (result) => {
        this.setState({
          cart: {
            orders: result.cart.orders
          },
          firstname: result.firstName,
          lastname: result.lastName,
          email: result.email,
          phonenumber: result.phoneNumber,
          credit: result.credit,
          orders: result.allOrders,
        });
      },
      // Note: it's important to handle errors here
      // instead of a catch() block so that we don't swallow
      // exceptions from actual bugs in components.
      (error) => {
        this.setState({
          isLoaded: true,
          error: error
        });
      }
    )
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
