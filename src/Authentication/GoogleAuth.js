import React from "react";
import {GoogleLogin} from 'react-google-login';
import google from '../Assets/google.png';
import swal from "sweetalert";
import {CLIENT_ID} from '../Constants/Constants';

function LoginButton(props) {
  return (
      <GoogleLogin
          render={renderProps => (
              <button className="btn btn-light google-login-btn" onClick={renderProps.onClick}>
                {props.text}
                <img src={google} alt="Google"/>
              </button>
          )}
          clientId={CLIENT_ID}
          buttonText= {props.text}
          onSuccess={props.login}
          onFailure={alertLoginFailure}
          cookiePolicy={'single_host_origin'}
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
  LoginButton
}