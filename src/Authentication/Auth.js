import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";
import swal from "sweetalert";

function authenticate(email, pass, isGoogleAuth) {
  let body;
  if (isGoogleAuth) {
    body = {
      email: email,
      password: "",
      isGoogleAuth: true,
      id_token: pass
    }
  }
  else {
    body = {
      email: email,
      password: pass,
      isGoogleAuth: false,
      id_token: ""
    }
  }
  return axios.post(SERVER_URI + "/login", body)
    .then((response) => {
      return response;
    })
    .catch((error) => {
      if (!isGoogleAuth)
      swal({
        title: "خطا",
        text: error.response.data.msg,
        icon: "error",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      })
      return error.response;
    })
}

function isAuthenticated() {
  return localStorage.getItem("jwt") !== null;
}

export {authenticate, isAuthenticated};