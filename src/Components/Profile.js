import React from 'react';
import {toPersianNum} from '../Utils/Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar';
import Modal from "react-bootstrap/Modal";
import CreditForm from '../Forms/CreditForm';
import CartBasedComponent from './CartBasedComponent';
import ClipLoader from 'react-spinners/ClipLoader';
import LoadingOverlay from 'react-loading-overlay';
import Error from '../Error/Error';
import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";

function Banner(props) {
  let name = props.firstname + ' ' + props.lastname;
  let email = props.email;
  let credit = props.credit;
  return (
    <header className="container-fluid banner row-sm-12">
      <div className="col-sm-6">
          <i className="flaticon-account"/>
          {name}
      </div>
      <div className="col-sm-6">
          <ul>
          <li>
              <i dir="ltr" className="flaticon-mail"/>
              {email}
          </li>
          <li>
              <i dir="ltr" className="flaticon-card"/>
              {toPersianNum(credit)} &nbsp;
              تومان
          </li>
          </ul>
      </div>
    </header>
  );
}

Banner.propTypes = {
  firstname: PropTypes.string,
  lastname: PropTypes.string,
  email: PropTypes.string,
  credit: PropTypes.number
};

Banner.defaultProps = {
  firstname: '',
  lastname: '',
  email: '',
  credit: 0
};

class Profile extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      firstname: '',
      lastname: '',
      email: '',
      credit: 0,
      orders: [],
      toShow: null,
      cart: {},
      error: null,
      isLoaded: false,
      orderToShow: null
    };
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.updateCredit = this.updateCredit.bind(this);
    this.showOrder = this.showOrder.bind(this);
    this.handleError = this.handleError.bind(this);
  }

  OrderList() {
    return (
      <div className="multiple-rows">
      {this.state.orders.slice(0).reverse().map((order, i) => (
        <div className="row" key={order.cartId}>
          <div className="col-1 col-bordered bg-light">
            {toPersianNum(i+1)}
          </div>
          <div className="col-7 col-bordered bg-light">
            {order.restaurantName}
          </div>
          <div className="col-4 col-bordered bg-light">
            {order.orderStatus === 1 &&
            <button disabled className="blue-btn small-btn">در جست‌و‌جوی پیک</button>
            }
            {order.orderStatus === 2 &&
              <button disabled className="green-btn small-btn">پیک در مسیر</button>
            }
            {order.orderStatus === 3 &&
              <button className="yellow-btn small-btn" onClick={() => this.showOrder(order.cartId)}>مشاهده فاکتور</button>
            }
          </div>
            {this.OrderDetails(order.cartId)}
        </div>
      ))}
    </div>
    );
  }

  showOrder(id) {
    this.fetchOrder(id);
    this.handleShow(id);
  }

  OrderDetails(id) {
    const toShow = this.state.toShow;
    const order = this.state.orderToShow;
    if (order === null || id !== order.id) return;
    if (toShow === id)
    return (
      <Modal className="modal fade" role="dialog"
        show={toShow === id}
        onHide={this.handleHide}>
        <Modal.Body>
          <h4>
            {order.restaurantName}
          </h4>
          <hr className="thin"/>
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
                <tr key={food.foodName}>
                  <th scope="row" className="text-center">{toPersianNum(i+1)}</th>
                  <td className="text-center">{food.foodName}</td>
                  <td className="text-center">{toPersianNum(food.number)}</td>
                  <td className="text-center">{toPersianNum(food.price * food.number)}</td>
                </tr>
              ))}
              </tbody>
            </table>
            <h6><b>
              جمع کل: {toPersianNum(this.getSum(order.orders))} تومان
            </b></h6>
          </div>
        </Modal.Body>
      </Modal>
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
    credit,
    orders,
    toShow,
    cart,
    error,
    isLoaded} = this.state;
    let cartOrdersLen = 0;
    let ordersLen = 0;
    if (cart.orders !== undefined && cart.orders !== null && cart.orders.length > 0)
      cartOrdersLen = cart.orders.length;
    if (orders !== undefined && orders !== null && orders.length > 0)
      ordersLen = orders.length;
    if (error) {
      return <Error code={500}/>;
    } else {
    return (
      <LoadingOverlay
        active={!isLoaded}
        spinner={<ClipLoader
          size={40}
          color={"#ff6b6b"}
          loading={!this.state.isLoaded}
        />}>
        <Navbar whereAmI="profile" cartCount={cartOrdersLen} func={this.handleShow} logout={this.logout}/>
        <Banner firstname={firstname} lastname={lastname} email={email} credit={credit}/>
        <div className="warpper">
          <input className="radio" id="one" name="group" type="radio" defaultChecked={true}/>
          <input className="radio" id="two" name="group" type="radio"/>
          <div className="tabs">
            <label className="tab" id="one-tab" htmlFor="one">سفارش ها</label>
            <label className="tab" id="two-tab" htmlFor="two">افزایش اعتبار</label>
          </div>
          <div className="panels">
              <div className="panel row-sm-5" id="one-panel">
              {ordersLen > 0
              ? this.OrderList()
              : <h1>سفارشی ثبت نشده است</h1>
              }
              </div>
              <div className="panel row-sm-5" id="two-panel">
              <CreditForm update={this.updateCredit} handleError={this.handleError}/>
              </div>
          </div>
        </div>
        <Modal className="modal fade" role="dialog"
          show={toShow === "cart"}
          onHide={this.handleHide}>
          <Modal.Body>
            <div id="cart">
              <div className="card">
              {cartOrdersLen > 0
              ? this.Cart()
              : <h1>سبد خرید شما خالی است</h1>
              }
              </div>
            </div>
          </Modal.Body>
        </Modal>
      </LoadingOverlay>
    );
    }
  }

  fetchProfile() {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.get(SERVER_URI + "/user", options)
    .then((response) => {
      this.setState({
        firstname: response.data.firstName,
        lastname: response.data.lastName,
        email: response.data.email,
        phonenumber: response.data.phoneNumber,
        credit: response.data.credit,
        error: (!this.state.error) ? response.data.msg : this.state.error,
        isLoaded: true,
      });
    },
      (error) => {
        this.handleError(error);
        this.setState({
          isLoaded: true,
          error: error
        });
      }
    );
  }

  fetchOrder(id) {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.get(SERVER_URI + "/orders/".concat(id), options)
    .then(
      (response) => {
        this.setState({
          orderToShow: response.data,
          error: (!this.state.error) ? response.data.msg : this.state.error
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

  componentDidMount() {
    this.fetchCart();
    this.fetchProfile();
    this.fetchOrders();
  }
}

export default Profile;
