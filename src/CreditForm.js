import React from 'react';
import {post, Form} from './Utils'

class CreditForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      amount: 0
    };
  }

  mySubmitHandler(event) {
    event.preventDefault();
    post(this.state.amount, 'http://localhost:8080/v1/credit');
  }

  render() {
    return (
      <form className="form-inline justify-content-center" id="credit" method="POST" onSubmit={this.mySubmitHandler}>
      <div className="form-group">
        <input type="text" className="form-control bg-light" placeholder="میزان افزایش اعتبار" onChange={this.myChangeHandler} />
      </div>
      <button type="submit" className="btn cyan-btn">افزایش</button>
    </form>
    );
  }
}

export default CreditForm;