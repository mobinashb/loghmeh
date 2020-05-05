import {Route, Redirect} from "react-router-dom";
import React from "react";
import authenticate from "./Auth";

function ProtectedRoute({ component: Component, ...rest }) {
  return (
      <Route
          {...rest}
          render={props =>
              authenticate() ? (
                  <Component {...props} />
              ) : (
                  <Redirect
                      to={{
                        pathname: "/login",
                        state: { from: props.location }
                      }}
                  />
              )
          }
      />
  );
}

export default ProtectedRoute;