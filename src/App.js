import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Home from './Home';
import LoginSignup from './LoginSignup';
import Profile from './Profile';
import json from './test.json'
import {Footer} from './Utils'
import Restaurant from './Restaurant'

class App extends React.Component {
  render() {
    return (
       <BrowserRouter>
        <div>
            <Switch>
             <Route path="/" component={Home} exact/>
             <Route path="/login" component={LoginSignup}/>
             <Route path="/profile" component={() => <Profile firstname="احسان" lastname="خامس پناه"
             email="ekhamespanah@yahoo.com" phonenumber="09123456789" credit={100000}
             orders={json.orders}
             cart={json.cart}
             />}/>
             <Route path="/restaurant" component={Restaurant} exact/>
           </Switch>
        </div>
        <Footer/>
      </BrowserRouter>
    );
  }
}


export default App;
