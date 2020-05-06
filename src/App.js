import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Home from './Components/Home';
import LoginSignup from './Components/LoginSignup';
import Profile from './Components/Profile';
import {Footer} from './Utils/Utils';
import Restaurant from './Components/Restaurant';
import Error from './Error/Error';
import ProtectedRoute from './Authentication/ProtectedRoute';
import PublicRoute from './Authentication/PublicRoute';

class App extends React.Component {
  render() {
    return (
       <BrowserRouter>
        <div>
            <Switch>
             <ProtectedRoute exact path="/" component={Home}/>
             <PublicRoute path="/login" component={LoginSignup}/>
             <ProtectedRoute path="/profile" component={Profile}/>
             <ProtectedRoute path="/restaurant" component={Restaurant}/>
             <Route component={() => <Error code={404}/>} />
           </Switch>
        </div>
        <Footer/>
      </BrowserRouter>
    );
  }
}


export default App;
