import React from "react";
import {GoogleLogin, GoogleLogout} from 'react-google-login';
import google from '../Assets/google.png';
import swal from "sweetalert";

function LogoutButton(props) {
  return (
      <GoogleLogout
          render={renderProps => (
              // eslint-disable-next-line
              <a className="nav-link logout" onClick={renderProps.onClick}>خروج</a>
          )}
          clientId="568052004069-80istmajegjol2dd97o4mt67imfemads.apps.googleusercontent.com"
          buttonText="Logout"
          onLogoutSuccess={props.logout}
      >
      </GoogleLogout>
  );
}

function LoginButton(props) {
  return (
      <GoogleLogin
          render={renderProps => (
              <button className="btn btn-light google-login-btn" onClick={renderProps.onClick}>
                {props.text}
                <img src={google} alt="Google"/>
              </button>
          )}
          clientId="568052004069-80istmajegjol2dd97o4mt67imfemads.apps.googleusercontent.com"
          buttonText= {props.text}
          onSuccess={props.login}
          onFailure={alertLoginFailure}
          cookiePolicy={'single_host_origin'}
          isSignedIn={true}
      />
  );
}

function alertLoginFailure() {
  swal({
    text: "خطا در ورود با گوگل",
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

export {
  LoginButton, LogoutButton
}