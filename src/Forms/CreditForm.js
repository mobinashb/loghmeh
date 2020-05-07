import React from 'react';
import swal from 'sweetalert';
import Form from './Form';
import PropTypes from "prop-types";
import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";

class CreditForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      amount: '',
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
    this.mySubmitHandler = this.mySubmitHandler.bind(this);
  }

  mySubmitHandler(event) {
    event.preventDefault();
    const jwt = localStorage.getItem("jwt");
    let options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.post(SERVER_URI + "/credit", this.state, options)
      .then((response) => {
        if (response.status === 200) {
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
      }, (error) => {
        this.props.handleError(error);
      }
    );
  }

  resetForm() {
    this.setState({ amount: null });
  }

  render() {
    const {amount} = this.state;
    return (
      <form className="form-inline justify-content-center" id="credit" onSubmit={this.mySubmitHandler} onReset={this.resetForm}>
      <div className="form-group">
        <input type="text" name="amount" value={amount} className="form-control bg-light" placeholder="میزان افزایش اعتبار" onChange={this.myChangeHandler} />
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