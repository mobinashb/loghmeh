import React from 'react';
import {POST} from '../Utils/Utils';
import swal from 'sweetalert';
import Form from './Form';
import PropTypes from "prop-types";

class CreditForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      amount: '',
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
    this.mySubmitHandler = this.mySubmitHandler.bind(this);
  }

  async mySubmitHandler(event) {
    event.preventDefault();
    let response = POST(this.state, 'http://localhost:8080/v1/credit');
    const res = await response;
    if (res.ok) {
      this.props.update(this.state.amount);
      this.setState({amount: ""});
      swal({
        text: "اعتبار شما با موفقیت افزایش یافت",
        icon: "success",
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      });
    }
  }

  resetForm() {
    this.setState({ amount: null });
  }

  render() {
    const {amount} = this.state;
    return (
      <form className="form-inline justify-content-center" id="credit" onSubmit={this.mySubmitHandler} onReset={this.resetForm}>
      <div className="form-group">
        <input type="text" id="amount" value={amount} className="form-control bg-light" placeholder="میزان افزایش اعتبار" onChange={this.myChangeHandler} />
      </div>
      <button type="submit" className="btn cyan-btn" disabled={isNaN(this.state.amount) || !this.state.amount}>افزایش</button>
    </form>
    );
  }
}

CreditForm.propTypes = {
  update: PropTypes.func
};

CreditForm.defaultProps = {
  update: null
};

export default CreditForm;