import React from 'react';
import logo from '../Assets/LOGO.png'
import cover from '../Assets/cover.jpg'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import '../CSS/styles.css';

function Header() {
  return (
    <header className="row-sm-12">
      <img src={cover} className="cover" alt="لقمه"/>
      <div className="title-description">
        <figure className="figure">
          <img src={logo} className="figure-img" alt="لقمه"/>
          <figcaption className="figure-caption shadowed-text">اولین و بزرگترین وب‌سایت سفارش آنلاین غذا در دانشگاه تهران</figcaption>
        </figure>
      </div>
    </header>
  );
  }

function Footer() {
  return (
    <footer>&copy; تمامی حقوق متعلق به لقمه است.</footer>
  );
}

class Form extends React.Component {
  myChangeHandler = (event) => {
    let nam = event.target.id;
    let val = event.target.value;
    this.setState({[nam]: val});
  }
}

function toPersianNum(inp) {
  var i = 0,
  num = inp.toString(),
  len = num.length,
  res = '',
  pos,
  persianNumbers =
      ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
  for (; i < len; i++)
      if (( pos = persianNumbers[num.charAt(i)] ))
          res += pos;
      else
          res += num.charAt(i);
  return res;
}

async function POST(body, path) {
  let response = await fetch(path, {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    }
  });
  return response;
}

async function PUT(body, path) {
  let response = await fetch(path, {
    method: 'PUT',
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    }
  });
  return response;
}

async function DELETE(body, path) {
  let response = await fetch(path, {
    method: 'DELETE',
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    }
  });
  return response;
}

function getQueryParams(url, name) {
  name = name.replace(/[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

export {
    Header,
    Footer,
    Form,
    toPersianNum,
    POST,
    PUT,
    DELETE,
    getQueryParams
}