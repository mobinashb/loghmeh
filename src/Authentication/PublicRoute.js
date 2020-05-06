import {Route, Redirect} from "react-router-dom";
import React from "react";
import {isAuthenticated} from "./Auth";

function ProtectedRoute({ component: Component, ...rest }) {
  return (
    <Route
      {...rest}
      render={props =>
        isAuthenticated() ?
        (
          <Redirect
            to={{
              pathname: "/",
              state: { from: props.location }
            }}
          />
        ) : <Component {...props} />
      }
    />
  );
}

export default ProtectedRoute;