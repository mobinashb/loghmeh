import React from "react";

class Form extends React.Component {
  myChangeHandler = (event) => {
    let nam = event.target.id;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
}

export default Form;