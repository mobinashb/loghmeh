import {Route, Redirect} from "react-router-dom";
import React from "react";
import Auth from "./Auth";

function ProtectedRoute({ component: Component, ...rest }) {
  return (
      <Route
          {...rest}
          render={props =>
              Auth() ? (
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