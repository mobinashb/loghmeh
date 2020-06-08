import Form from "./Form";
import {authenticate} from "../Authentication/Auth";
import {LoginButton} from "../Authentication/GoogleAuth";
import React from "react";
import swal from "sweetalert";
import ClipLoader from "react-spinners/ClipLoader";
import LoadingOverlay from "react-loading-overlay";

class LoginForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      loggedIn: false,
      isLoaded: true
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
    this.login = this.login.bind(this);
  }

  login(googleUser) {
    const profile = googleUser.getBasicProfile();
    const email = profile.getEmail();
    const id_token = googleUser.getAuthResponse().id_token;
    authenticate(email, id_token, true)
      .then((response) => {
        this.setState({
          isLoaded: true
        });
        if (response !== null && response.status === 200) {
          localStorage.setItem("jwt", response.data);
          this.setState({
            loggedIn: true
          });
          this.props.redirect('/');
        }
        else {
          const auth2 = window.gapi.auth2.getAuthInstance();
          if (auth2 != null) {
            auth2.signOut().then(
                auth2.disconnect().then(() => {
                  if (response.status !== null && response.status !== undefined && response.status !== 500)
                    swal({
                      text: response.data.msg,
                      icon: "error",
                      dangerMode: true,
                      button: {
                        text: "بستن",
                        value: null,
                        visible: true,
                        closeModal: true,
                      },
                    })
                  })
            )
          }
        }
      });
  }

  mySubmitHandler = (event) => {
    event.preventDefault();
    this.setState({
      isLoaded: false
    });
    const email = event.target.email.value;
    const password = event.target.password.value;
    authenticate(email, password, false)
      .then((response) => {
        this.setState({
          isLoaded: true
        });
        if (response.status === 200) {
          localStorage.setItem("jwt", response.data);
          this.setState({
            loggedIn: true
          });
          this.props.redirect('/');
        }
      });
  }

  render() {
    const buttonText = this.state.loggedIn ? "وارد شده" : "ورود با گوگل";
    const isLoaded = this.state.isLoaded;
    return(
        <LoadingOverlay
          active={!isLoaded}
          spinner={<ClipLoader
              size={40}
              color={"#ff6b6b"}
              loading={!isLoaded}
        />}>
          <form className="text-center p-5" id="login" onSubmit={this.mySubmitHandler}>
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
              <LoginButton text={buttonText} login={this.login}/>
            </div>
          </form>
        </LoadingOverlay>
    );
  }
}

export default LoginForm;