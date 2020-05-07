import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";
import swal from "sweetalert";

function authenticate(email, password, isGoogleAuth) {
  console.log(email, password, isGoogleAuth);
  let body = {
    email: email,
    password: password
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