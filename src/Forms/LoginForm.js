import Form from "./Form";
import authenticate from "../Authentication/Auth";
import {LoginButton} from "../Authentication/GoogleAuth";
import React from "react";

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
    const profile = googleUser.getBasicProfile();
    const email = profile.getEmail();
    this.setState({
      loggedIn: true
    });
    if (authenticate(email, true) !== null) {
      localStorage.setItem("token", profile.getEmail());
      console.log(localStorage.getItem("token"))
    }
    else {
      this.props.toSignup();
    }
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

export default LoginForm;