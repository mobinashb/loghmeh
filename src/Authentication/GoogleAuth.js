import React from "react";
import {GoogleLogin, GoogleLogout} from 'react-google-login';
import google from '../Assets/google.png';

function LogoutButton() {
  return (
      <GoogleLogout
          render={renderProps => (
              <a className="nav-link logout" onClick={renderProps.onClick} href="/login">خروج</a>
          )}
          clientId="568052004069-80istmajegjol2dd97o4mt67imfemads.apps.googleusercontent.com"
          buttonText="Logout"
          onLogoutSuccess={onLogout}
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
          onSuccess={props.onSignIn}
          cookiePolicy={'single_host_origin'}
          isSignedIn={true}
      />
  );
}

function onLogout() {
  console.log('logged out!');
}

export {
  LoginButton, LogoutButton
}