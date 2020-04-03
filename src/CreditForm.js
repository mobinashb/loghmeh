import React from 'react';
import {post, Form} from './Utils'

class CreditForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      amount: undefined,
      update: props.update
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
    this.mySubmitHandler = this.mySubmitHandler.bind(this);
  }

  mySubmitHandler(event) {
    event.preventDefault();
    console.log(JSON.stringify(this.state));
    post(this.state, 'http://localhost:8080/v1/credit');
    this.state.update(this.state.amount);
  }

  render() {
    return (
      <form className="form-inline justify-content-center" id="credit" action="#!" onSubmit={this.mySubmitHandler}>
      <div className="form-group">
        <input type="text" id="amount" className="form-control bg-light" placeholder="میزان افزایش اعتبار" onChange={this.myChangeHandler} />
      </div>
      <button type="submit" className="btn cyan-btn" disabled={isNaN(this.state.amount) || !this.state.amount}>افزایش</button>
    </form>
    );
  }
}

export default CreditForm;