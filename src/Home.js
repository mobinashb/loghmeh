import React from 'react';
import {Header, Footer, toPersianNum} from './Utils'
import star from './Assets/star.png'
import pizza from "./Assets/pizza.jpg"
import FoodDetails from './FoodDetails'

function Search() {
  return (
    <div class="search centered-flex">
      <form class="form-inline justify-content-center shadow-box" action="search">
        <div class="form-group">
          <input type="text" class="form-control bg-light" id="foodname" placeholder="نـــام غـــذا" />
          <input type="text" class="form-control bg-light" id="restaurantname" placeholder="نـــام رســـتـــوران" />
        </div>
        <button type="submit" class="btn btn-default">جســـت‌و‌جـــو</button>
      </form>
    </div>
  );
}

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      restaurants: []
    };
  }
  render() {
    // const { error, isLoaded, restaurants } = this.state;
    // if (error) {
    //   return <div>Error: {error.message}</div>;
    // } else if (!isLoaded) {
    //   return <div>Loading...</div>;
    // } else {
      return (
      <body>
        <Header/>
        <Search/>
        {/* <ul>
        {restaurants.map(item => (
          <li key={item.id}>
            {item.name}
          </li>
        ))}
      </ul> */}
        <div class="menu">
          <div class="title">
            جشن غذا!
          </div>
          <div class="centered-flex">
            <div class="timer">
              زمان باقی مانده: 21:48
            </div>
          </div>
          <div class="scrolling-wrapper shadow-box">
            <div class="card shadow-box col-sm-2">
              <img src="Assets/pizza.jpg" alt="pizza"/>
              <div class="food-title">
                  پیتزا نیمه اعلا
                  <span class="rating">
                      ۴
                      <img src={star}  alt=""/>
                  </span>
              </div>
              <h6>۲۹۰۰۰ تومان</h6>

              <button disabled class="in-stock">
                موجودی: 0
              </button>
              <button class="cyan-btn" disabled >خرید</button>
              <hr class="dashed-top"></hr>
              رستوران خامس
            </div>
            <div class="card shadow-box col-sm-2">
              <div class="row">
                <div class="col-sm-2">
                  <img src={pizza} alt="pizza"/>
                </div>
                <div class="col-sm-2">
                  <div class="food-title">
                  پیتزا نیمه اعلا
                  </div>
                  <span class="rating inline">
                    ۴
                    <img src={star}  alt=""/>
                  </span>
                </div>
              </div>
              <ul class="price-old-new">
              <li h6 class="striked-through">
                  ۲۹۰۰۰
                </li>
                <li>
                  ۲۹۰۰۰
                </li>
              </ul>
              <button disabled class="in-stock">
                موجودی: 3
              </button>
              <button class="cyan-btn" data-toggle="modal" data-target="#foodDetails">خرید</button>
              <hr class="dashed-top"></hr>
              رستوران خامس
            </div>
            <FoodDetails name="پیتزای اعلاء" restaurantName="رستوران خامس" restaurantId="abc"
            description="تهیه شده از بهترین مواد اولیه"
            price="29000"
            rating="5"
            count="3"
            oldPrice="39000"
            img={pizza}
            />
          </div>
        </div>
        <div class="menu container">
          <div class="title">
            رستوران ها
          </div>
          <div class="row">
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="card shadow-box">
                    <img src="Assets/pizza.jpg" alt="pizza"/>
                    <div class="food-title">
                        رستوران خامس
                    </div>
                    <button class="small-btn yellow-btn">نمایش منو</button>
                </div>
            </div>
          </div>
        </div>
        <Footer/>
      </body>
    );
    // }
    //     ;
  }

  componentDidMount() {
    fetch("http://localhost:8080/restaurants")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            restaurants: result.restaurants
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      )
  }
}

export default Home;
