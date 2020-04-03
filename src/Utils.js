import React from 'react';
import logo from './Assets/LOGO.png'
import cover from './Assets/cover.jpg'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import './CSS/styles.css';

function Header() {
  return (
    <header class="row-sm-12">
      <img src={cover} class="cover" alt="لقمه"/>
      <div class="title-description">
        <figure class="figure">
          <img src={logo} class="figure-img" alt="لقمه"/>
          <figcaption class="figure-caption">اولین و بزرگترین وب‌سایت سفارش آنلاین غذا در دانشگاه تهران</figcaption>
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

function toPersianTime(seconds) {
  var hours = 0,
  minutes = 0,
  res = '';
  while (seconds >= 60) {
    seconds -= 60;
    minutes += 1;
  }
  while (minutes >= 60) {
    minutes -= 60;
    hours += 1;
  }
  if (hours > 0) {
    res += toPersianNum(hours) + ':';
  }
  if (minutes > 0) {
    res += toPersianNum(minutes) + ':';
  }
  res += toPersianNum(seconds)
  return res;
}

async function post(body, path) {
  const response = await fetch(path, {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    }
  });
}

export {
    Header,
    Footer,
    Form,
    toPersianNum,
    toPersianTime,
    post,
}