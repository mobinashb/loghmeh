import React from 'react';
import {Header} from '../Utils/Utils';
import Panels from './Panels';

class LoginSignup extends React.Component {
  constructor(props) {
    super(props);
    this.redirect = this.redirect.bind(this);
  }
  render() {
    return (
      <div>
        <Header/>
        <Panels redirect={this.redirect}/>
      </div>
    );
  }

  redirect(path) {
    this.props.history.push(path);
  }
}

export default LoginSignup;