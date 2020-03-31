import React from 'react';
import {Footer, toPersianNum} from './Utils'
import PropTypes from 'prop-types';
import Navbar from './Navbar'

class Banner extends React.Component {
  render() {
    let name = this.props.firstname + ' ' + this.props.lastname;
    let email = this.props.email;
    let phonenumber = this.props.phonenumber;
    let credit = this.props.credit;
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
}

class Profile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: props.firstname,
      lastname: props.lastname,
      email: props.email,
      phonenumber: props.phonenumber,
      credit: props.credit

    }
  }
  render() {
    return (
      <body>
        <Navbar inProfile="false" cartCount="3"/>
        <Banner firstname={this.state.firstname} lastname={this.state.lastname} email={this.state.email} phonenumber={this.state.phonenumber} credit={this.state.credit}/>
        <Footer/>
      </body>
    );
  }
}

Profile.propTypes = {
  firstname: PropTypes.string,
  lastname: PropTypes.string,
  email: PropTypes.string,
  phonenumber: PropTypes.string,
  credit: PropTypes.number
}

export default Profile;
