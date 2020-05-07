import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";

async function authenticate(email, password, isGoogleAuth) {
  console.log(email, password, isGoogleAuth);
  let body = {
    email: email,
    password: password
  }
  return await axios.post(SERVER_URI + "/login", body);
}

function isAuthenticated() {
  return localStorage.getItem("jwt");
}

export {authenticate, isAuthenticated};