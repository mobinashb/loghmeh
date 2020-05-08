import Form from "./Form";
import swal from "sweetalert";
import React from "react";
import {SERVER_URI} from "../Constants/Constants";
import axios from "axios";
import LoadingOverlay from "react-loading-overlay";
import ClipLoader from "react-spinners/ClipLoader";

class SignupForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      isLoaded: true
    };
    this.myChangeHandler = this.myChangeHandler.bind(this);
  }
  mySubmitHandler = (event) => {
    event.preventDefault();
    let password = event.target.password;
    let passwordRep = event.target.passwordrepeat;
    let firstName = event.target.firstName;
    let lastName = event.target.lastName;
    let email = event.target.email;
    let name = (firstName.value).concat(lastName.value);
    if (!(name.match(/^[a-zA-Z]+$/)) && !(name.match(/^[\u0600-\u06FF\s]+$/))) {
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
    } else {
      this.setState({
        isLoaded: false
      });
      let body = this.state;
      axios.post(SERVER_URI + "/user", body)
        .then((response) => {
          this.setState({
            isLoaded: true
          });
          if (response.status === 200) {
            this.setState({
              firstName: '',
              lastName: '',
              email: '',
              password: '',
            })
            password.value = '';
            passwordRep.value = '';
            firstName.value = '';
            lastName.value = '';
            email.value = '';
            swal({
              text: response.data.msg,
              icon: "success",
              button: {
                text: "بستن",
                value: null,
                visible: true,
                closeModal: true,
              },
            })
            this.props.redirect("/login");
          }
        }, (error) => {
          this.setState({
            isLoaded: true
          });
          swal({
            title: "خطا",
            text: error.response.data.msg,
            icon: "warning",
            dangerMode: true,
            button: {
              text: "بستن",
              value: null,
              visible: true,
              closeModal: true,
            },
          })
        });
    }
  }

  render() {
    const isLoaded = this.state.isLoaded;
    return(
        <LoadingOverlay
        active={!isLoaded}
        spinner={<ClipLoader
            size={40}
            color={"#ff6b6b"}
            loading={!isLoaded}
        />}>
          <form className="text-center p-5" id="signup" onSubmit={this.mySubmitHandler}>
            <p className="h4 mb-4">ثبت نام</p>
            <input type="text" required name="firstName" className="form-control mb-4" placeholder="نام" onChange={this.myChangeHandler}/>
            <input type="text" required name="lastName" className="form-control mb-4" placeholder="نام خانوادگی" onChange={this.myChangeHandler}/>
            <input type="email" required name="email" className="form-control mb-4" placeholder="ایمیل" onChange={this.myChangeHandler}/>
            <input type="password" required name="password" className="form-control mb-4" placeholder="رمز عبور" onChange={this.myChangeHandler}/>
            <input type="password" required name="passwordrepeat" className="form-control mb-4" placeholder="تکرار رمز عبور"/>
            <button className="btn cyan-btn" type="submit">ثبت نام</button>
          </form>
        </LoadingOverlay>
    );
  }
}

export default SignupForm;