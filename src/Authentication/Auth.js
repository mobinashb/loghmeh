function authenticate(email, password, isGoogleAuth) {
  console.log(email, password, isGoogleAuth);
  let response = null;
  let body = {
    email: email,
    password: password
  }
  fetch("http://localhost:8080/v1/login", {
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
  return ["jwt", response];
}

function isAuthenticated() {
  return true;
}

export {authenticate, isAuthenticated};