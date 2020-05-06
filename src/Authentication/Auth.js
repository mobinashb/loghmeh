import {SERVER_URI} from "../Constants/Constants";
import {POST} from "../Utils/Utils";
import swal from "sweetalert";

async function authenticate(email, password, isGoogleAuth) {
  console.log(email, password, isGoogleAuth);
  let body = {
    email: email,
    password: password
  }
  let response = POST(body, SERVER_URI + "/login", false);
  const res = await response;
  const text = await (res).text();
  if (res.ok) {
    return text;
  } else {
    swal({
      title: "خطا",
      text: JSON.parse(text).msg,
      icon: "warning",
      dangerMode: true,
      button: {
        text: "بستن",
        value: null,
        visible: true,
        closeModal: true,
      },
    })
  }
  return null;
}

function isAuthenticated() {
  const jwt = localStorage.getItem("jwt");
  return jwt;
}

export {authenticate, isAuthenticated};