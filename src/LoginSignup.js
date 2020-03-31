import React from 'react';
import {Header, Footer, Form} from './Utils'

class LoginSignup extends React.Component {
    render() {
      return (
        <body>
          <Header/>
          <Panels/>
          <Footer/>
        </body>
      );
    }
  }

  class LoginForm extends Form {
    constructor(props) {
      super(props);
      this.state = {
        email: '',
        password: ''
      };
    }
    render() {
      return(
        <form class="text-center p-5" action="#!" id="login">
          <p class="h4 mb-4">ورود</p>
          <input type="email" id="email" class="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
          <input type="password" id="password" class="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
          <div class="d-flex justify-content-around">
              <div>
                  <div class="custom-control custom-checkbox">
                      <input type="checkbox" class="custom-control-input" id="remember"/>
                      <label class="custom-control-label" for="remember">مرا به خاطر بسپار</label>
                  </div>
              </div>
          </div>
          <button class="btn cyan-btn" type="submit">ورود</button>
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
    }
    mySubmitHandler = (event) => {
      let password = event.target.password;
      let passwordRep = event.target.passwordrepeat;
      if (password.value !== passwordRep.value) {
        event.preventDefault();
        alert("Passwords do not match!");
      }
    }
    render() {
      return (
        <form class="text-center p-5" action="#" id="signup" onSubmit={this.mySubmitHandler}>
          <p class="h4 mb-4">ثبت نام</p>
          <input type="text" id="firstname" class="form-control mb-4" placeholder="نام" onChange={this.myChangeHandler}/>
          <input type="text" id="lastname" class="form-control mb-4" placeholder="نام خانوادگی" onChange={this.myChangeHandler}/>
          <input type="email" id="email" class="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
          <input type="text" id="phonenumber" class="form-control mb-4" placeholder="شماره تماس" onChange={this.myChangeHandler}/>
          <input type="password" id="password" class="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
          <input type="password" id="passwordrepeat" class="form-control mb-4" placeholder="تکرار رمز عبور"/>
          <button class="btn cyan-btn" type="submit">ثبت نام</button>
        </form>
      );
    }
  }

function Panels() {
    return (
        <div class="warpper">
            <input class="radio" id="one" name="group" type="radio" checked/>
            <input class="radio" id="two" name="group" type="radio"/>
            <div class="tabs">
            <label class="tab" id="one-tab" for="one">ورود</label>
            <label class="tab" id="two-tab" for="two">ثبت نام</label>
            </div>
            <div class="panels">
                <div class="panel row-sm-5" id="one-panel">
                <LoginForm/>
                </div>
                <div class="panel row-sm-5" id="two-panel">
                <SignupForm/>
                </div>
            </div>
        </div>
    );
}

export default LoginSignup;