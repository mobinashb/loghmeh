import React from 'react';
import {Header} from '../Utils/Utils';
import Panels from './Panels';

class LoginSignup extends React.Component {
  render() {
    return (
      <div>
        <Header/>
        <Panels/>
      </div>
    );
  }
}

export default LoginSignup;