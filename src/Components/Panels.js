import React from "react";
import LoginForm from '../Forms/LoginForm';
import SignupForm from '../Forms/SignupForm';

class Panels extends React.Component {
  render() {
    return (
        <div className="warpper">
          <input className="radio" id="one" name="group" type="radio" defaultChecked={true}/>
          <input className="radio" id="two" name="group" type="radio"/>
          <div className="tabs">
            <label className="tab" id="one-tab" htmlFor="one">ورود</label>
            <label className="tab" id="two-tab" htmlFor="two">ثبت نام</label>
          </div>
          <div className="panels">
            <div className="panel row-sm-5" id="one-panel">
              <LoginForm redirect={this.props.redirect}/>
            </div>
            <div className="panel row-sm-5" id="two-panel">
              <SignupForm redirect={this.props.redirect}/>
            </div>
          </div>
        </div>
    );
  }
}

export default Panels;