import {SERVER_URI} from "../Constants/Constants";

function authenticate(email, password, isGoogleAuth) {
  console.log(email, password, isGoogleAuth);
  let response = null;
  let body = {
    email: email,
    password: password
  }
  fetch(SERVER_URI + "/login", {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        }
      }
  )
      .then(res => res.json())
      .then(
          (result) => {
            response = result.msg;
          }
      );
  return [null, response];
}

function isAuthenticated() {
  return true;
}

export {authenticate, isAuthenticated};