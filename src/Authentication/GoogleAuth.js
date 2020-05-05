import {gapi} from "gapi-script";
import React from "react";
import {GoogleLogin, GoogleLogout} from 'react-google-login';

function LogoutButton() {
  return (
      <GoogleLogout
          render={() => (
              <a className="nav-link logout">خروج</a>
          )}
          clientId="568052004069-80istmajegjol2dd97o4mt67imfemads.apps.googleusercontent.com"
          buttonText="Logout"
          onLogoutSuccess={console.log("signed outtttt")}
      >
      </GoogleLogout>
  );
}

function LoginButton(props) {
  return (
      <GoogleLogin
          clientId="568052004069-80istmajegjol2dd97o4mt67imfemads.apps.googleusercontent.com"
          buttonText= {props.text}
          onSuccess={props.onSignIn}
          cookiePolicy={'single_host_origin'}
          isSignedIn={true}
      />
  );
}

export {
  LoginButton, LogoutButton
}