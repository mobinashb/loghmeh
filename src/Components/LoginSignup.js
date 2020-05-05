import React from 'react';
import {Header} from '../Utils/Utils';
import swal from "sweetalert";
import Form from './Form';
import {LoginButton} from '../Authentication/GoogleAuth';

function Panels() {
  return (
    <div className="warpper">
      <input className="radio" id="one" name="group" type="radio" defaultChecked={true}/>
      <input className="radio" id="two" name="group" type="radio"/>
      <div className="tabs">
      <label className="tab" id="one-tab" htmlFor="one">ورود</label>
      <label className="tab" id="two-tab" htmlFor="two">ثبت نام</label>
      </div>
      <div className="panels">
          <div className="panel row-sm-5" id="one-panel">
          <LoginForm/>
          </div>
          <div className="panel row-sm-5" id="two-panel">
          <SignupForm/>
          </div>
      </div>
    </div>
  );
}
class LoginSignup extends React.Component {
  render() {
    return (
      <div>
        <Header/>
        <Panels/>
      </div>
    );
  }
}

class LoginForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      loggedIn: false
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
    this.onSignIn = this.onSignIn.bind(this);
  }

  onSignIn(googleUser) {
    console.log("hi");
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    this.setState({
      loggedIn: true
    });
  }

  render() {
    const buttonText = this.state.loggedIn ? "Signed in" : "Sign in";
    return(
      <form className="text-center p-5" action="/" id="login">
        <p className="h4 mb-4">ورود</p>
        <input type="email" required name="email" className="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
        <input type="password" required name="password" className="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
        <div className="d-flex justify-content-around">
            <div>
                <div className="custom-control custom-checkbox">
                    <input type="checkbox" className="custom-control-input" id="remember"/>
                    <label className="custom-control-label" htmlFor="remember">مرا به خاطر بسپار</label>
                </div>
            </div>
        </div>
        <div className="d-flex justify-content-around">
          <button className="btn cyan-btn" type="submit">ورود</button>
        </div>
        <div className="d-flex justify-content-around google-login-btn">
          <LoginButton text={buttonText} onSignIn={this.onSignIn}/>
        </div>
      </form>
    );
  }
}

class SignupForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      firstname: '',
      lastname: '',
      email: '',
      phonenumber: '',
      password: ''
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
  }
  mySubmitHandler = (event) => {
    let password = event.target.password;
    let passwordRep = event.target.passwordrepeat;
    let phoneNum = event.target.phonenumber;
    let name = (event.target.firstname.value).concat(event.target.lastname.value);
    if (!(name.match(/^[a-zA-Z]+$/)) && !(name.match(/^[\u0600-\u06FF\s]+$/))) {
      event.preventDefault();
      swal({
        title: "خطا",
        text: "نام و نام خانوادگی شما اشتباه وارد شده است!",
        icon: "error",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      });
      return
    }
    if (!phoneNum.value.match(/^[0-9]+$/)) {
      event.preventDefault();
      swal({
        title: "خطا",
        text: "شماره تلفن اشتباه وارد شده است!",
        icon: "error",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      });
      return
    }
    if (password.value !== passwordRep.value) {
      event.preventDefault();
      swal({
        title: "خطا",
        text: "تکرار رمز عبور اشتباه وارد شده است!",
        icon: "error",
        dangerMode: true,
        button: {
            text: "بستن",
            value: null,
            visible: true,
            closeModal: true,
        },
      });
    }
  }

  render() {
    return (
      <form className="text-center p-5" action="#" id="signup" onSubmit={this.mySubmitHandler}>
        <p className="h4 mb-4">ثبت نام</p>
        <input type="text" required name="firstname" className="form-control mb-4" placeholder="نام" onChange={this.myChangeHandler}/>
        <input type="text" required name="lastname" className="form-control mb-4" placeholder="نام خانوادگی" onChange={this.myChangeHandler}/>
        <input type="email" required name="email" className="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
        <input type="text" required name="phonenumber" className="form-control mb-4" placeholder="شماره تماس" onChange={this.myChangeHandler}/>
        <input type="password" required name="password" className="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
        <input type="password" required name="passwordrepeat" className="form-control mb-4" placeholder="تکرار رمز عبور"/>
        <button className="btn cyan-btn" type="submit">ثبت نام</button>
      </form>
    );
  }
}

export default LoginSignup;