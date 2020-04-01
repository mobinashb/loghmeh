import React from 'react';
import {Footer, toPersianNum} from './Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar'

function Banner(props) {
  let name = props.firstname + ' ' + props.lastname;
  let email = props.email;
  let phonenumber = props.phonenumber;
  let credit = props.credit;
  return (
    <header class="container-fluid banner row-sm-12">
      <div class="col-sm-6">
          <i class="flaticon-account"></i>
          {name}
      </div>
      <div class="col-sm-6">
          <ul>
          <li>
              <i dir="ltr" class="flaticon-phone"></i>
              {phonenumber}
          </li>
          <li>
              <i dir="ltr" class="flaticon-mail"></i>
              {email}
          </li>
          <li>
              <i dir="ltr" class="flaticon-card"></i>
              {toPersianNum(credit)} &nbsp;
              تومان
          </li>
          </ul>
      </div>
    </header>
  );
}

function OrderButton(props) {
  let id = "#" + props.id;
  if (props.status === "delivering") {
    return <button disabled class="green-btn small-btn">پیک در مسیر</button>
  }
  if (props.status === "finding delivery") {
    return <button disabled class="blue-btn small-btn" data-toggle="modal" data-target={id}>در جست‌و‌جوی پیک</button>
  }
  if (props.status === "delivered") {
    return <button class="yellow-btn small-btn" data-toggle="modal" data-target={id}>مشاهده فاکتور</button>
  }
}

function FoodInTable(params) {
  return (<tr>
    <th scope="row" class="text-center">{params.i}</th>
    <td class="text-center">{params.name}</td>
    <td class="text-center">{params.count}</td>
    <td class="text-center">{params.price}</td>
  </tr>
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
      orders: props.orders
    }
  }
  render() {
    const orders = this.state.orders;
    return (
      <body>
        <Navbar inProfile="false" cartCount="3"/>
        <Banner firstname={this.state.firstname} lastname={this.state.lastname} email={this.state.email} phonenumber={this.state.phonenumber} credit={this.state.credit}/>
        <div class="warpper">
          <input class="radio" id="one" name="group" type="radio" checked />
          <input class="radio" id="two" name="group" type="radio" />
          <div class="tabs">
          <label class="tab" id="one-tab" for="one">سفارش ها</label>
          <label class="tab" id="two-tab" for="two">افزایش اعتبار</label>
            </div>
          <div class="panels">
            <div class="panel row-sm-5" id="one-panel">
              {orders.map((order, i) => (
                <div class="row">
                  <div class="col-1 col-bordered bg-light">
                    {toPersianNum(i)}
                  </div>
                  <div class="col-7 col-bordered bg-light">
                    {order.restaurantName}
                  </div>
                  <div class="col-4 col-bordered bg-light">
                    <OrderButton id={order.id} status={order.status} />
                  </div>
                </div>
              ))}
            </div>
            <div class="panel row-sm-5" id="two-panel">
              <form class="form-inline justify-content-center" action="addcredit" id="credit" method="POST">
                <div class="form-group">
                  <input type="text" class="form-control bg-light" placeholder="میزان افزایش اعتبار" />
                </div>
                <button type="submit" class="btn btn-default">افزایش</button>
              </form>
            </div>
          </div>
        </div>
        <Footer/>
        {orders.map(order => (
          <div class="modal fade" id={order.id} tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-body">
                  <h2>
                    {order.restaurantName}
                  </h2>
                  <div class="underline"></div>
                  <div class="table-responsive bg-white">
                    <table class="table table-bordered table-small">
                      <thead class="thead-light">
                        <tr>
                          <th scope="col" class="text-center">ردیف</th>
                          <th scope="col" class="text-center">نام غذا</th>
                          <th scope="col" class="text-center">تعداد</th>
                          <th scope="col" class="text-center">قیمت</th>
                        </tr>
                      </thead>
                      <tbody>
                      {order.foodList.map((food, i) => (
                        <FoodInTable i={i} name={food.name} count={food.count} price={food.price} />
                      ))}
                      </tbody>
                    </table>
                    <p class="bold">
                      جمع کل: {toPersianNum(order.sum)} تومان
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </body>
    );
  }
}

Profile.propTypes = {
  firstname: PropTypes.string,
  lastname: PropTypes.string,
  email: PropTypes.string,
  phonenumber: PropTypes.string,
  credit: PropTypes.number,
  orders: PropTypes.array
}

export default Profile;
