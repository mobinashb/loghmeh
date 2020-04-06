import React from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import './CSS/styles.css';
import cover from './Assets/cover.jpg';
import error from './Assets/error.png';
import {Link} from 'react-router-dom';

class Error extends React.Component {
  render() {
    var errorMessage;
    var button = "";
    if (this.props.code === 500) {
      errorMessage = "خطا در برقراری ارتباط با سرور";
    }
    if (this.props.code === 404) {
      errorMessage = "صفحه یافت نشد!";
      button = <div><Link to="" className="btn btn-danger">بازگشت به صفحه اصلی</Link></div>;
    }
    return (
      <div className="error-page">
        <img src={cover} className="cover" alt="لقمه"/>
        <div className="title-description">
          <div className="error-text shadowed-text shadow-box">
            <div><img src={error} alt="خطا"/></div>
              {errorMessage}
              {button}
            </div>
        </div>
      </div>
    )
  }
}

export default Error;