import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Home from './Components/Home';
import LoginSignup from './Components/LoginSignup';
import Profile from './Components/Profile';
import {Footer} from './Utils/Utils';
import Restaurant from './Components/Restaurant';
import Error from './Error/Error';

class App extends React.Component {
  render() {
    return (
       <BrowserRouter>
        <div>
            <Switch>
             <Route path="/" component={Home} exact/>
             <Route path="/login" component={LoginSignup}/>
             <Route path="/profile" component={Profile}/>
             <Route path="/restaurant" component={Restaurant} exact/>
             <Route component={() => <Error code={404}/>} />
           </Switch>
        </div>
        <Footer/>
      </BrowserRouter>
    );
  }
}


export default App;
