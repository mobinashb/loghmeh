import Form from "./Form";
import {authenticate} from "../Authentication/Auth";
import {LoginButton} from "../Authentication/GoogleAuth";
import React from "react";
import swal from "sweetalert";

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
    let jwt = authenticate(email, true);
    if (jwt) {
      localStorage.setItem("jwt", jwt);
      console.log(localStorage.getItem("jwt"))
    }
    else {
      const auth2 = window.gapi.auth2.getAuthInstance()
      if (auth2 != null) {
        swal({
          text: "شما قبلا ثبت نام نکرده اید!",
          icon: "warning",
          dangerMode: true,
          button: {
            text: "بستن",
            value: null,
            visible: true,
            closeModal: true,
          },
        });
        auth2.signOut().then(
            auth2.disconnect().then(this.props.onLogoutSuccess)
        )
      }
    }
  }

  render() {
    const buttonText = this.state.loggedIn ? "وارد شده" : "ورود با گوگل";
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
          <div className="d-flex justify-content-around">
            <LoginButton text={buttonText} onSignIn={this.onSignIn}/>
          </div>
        </form>
    );
  }
}

export default LoginForm;