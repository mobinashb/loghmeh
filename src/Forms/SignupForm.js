import Form from "./Form";
import swal from "sweetalert";
import React from "react";

class SignupForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      firstname: '',
      lastname: '',
      email: '',
      password: ''
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
  }
  mySubmitHandler = (event) => {
    let password = event.target.password;
    let passwordRep = event.target.passwordrepeat;
    let name = (event.target.firstname.value).concat(event.target.lastname.value);
    if (!(name.match(/^[a-zA-Z]+$/)) && !(name.match(/^[\u0600-\u06FF\s]+$/))) {
      event.preventDefault();
      swal({
        title: "خطا",
        text: "نام و نام خانوادگی شما اشتباه وارد شده است!",
        icon: "error",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      });
      return
    }
    if (password.value !== passwordRep.value) {
      event.preventDefault();
      swal({
        title: "خطا",
        text: "تکرار رمز عبور اشتباه وارد شده است!",
        icon: "error",
        dangerMode: true,
        button: {
          text: "بستن",
          value: null,
          visible: true,
          closeModal: true,
        },
      });
    }
  }

  render() {
    return (
        <form className="text-center p-5" action="#" id="signup" onSubmit={this.mySubmitHandler}>
          <p className="h4 mb-4">ثبت نام</p>
          <input type="text" required name="firstname" className="form-control mb-4" placeholder="نام" onChange={this.myChangeHandler}/>
          <input type="text" required name="lastname" className="form-control mb-4" placeholder="نام خانوادگی" onChange={this.myChangeHandler}/>
          <input type="email" required name="email" className="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
          <input type="password" required name="password" className="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
          <input type="password" required name="passwordrepeat" className="form-control mb-4" placeholder="تکرار رمز عبور"/>
          <button className="btn cyan-btn" type="submit">ثبت نام</button>
        </form>
    );
  }
}

export default SignupForm;