import React from 'react';
import {Header, Footer, toPersianTime} from './Utils'
import FoodDetails from './FoodDetails'
import CartBasedComponent from './CartBasedComponent';

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

function RestaurantList(props) {
  let restaurantList = [];
  let rowContent = [];
  props.restaurants.forEach((item, i) =>{
    if ((i+1) % 3 === 0) {
      restaurantList.push(<div className="row">{rowContent}</div>);
      rowContent = [];
    }
    else {
      rowContent.push(
        <div class="col-sm-3">
          <div class="card shadow-box">
              <img src={item.logo} alt={item.name}/>
              <div class="food-title">
                {item.name}
              </div>
              <button class="small-btn yellow-btn">نمایش منو</button>
          </div>
        </div>
      )
    }
  })
  return restaurantList;
}

class Home extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      restaurants: [],
      foodPartyList: [],
      timeLeft: 0
    };
  }
  render() {
    const { error, isLoaded, restaurants, foodPartyList, timeLeft} = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
      <body>
        <Header/>
        <Search/>
        <div class="menu">
          <div class="title">
            جشن غذا!
          </div>
          <div class="centered-flex">
            <div class="timer">
              زمان باقی مانده: {toPersianTime(timeLeft)}
            </div>
          </div>
          <div class="scrolling-wrapper shadow-box">
            {foodPartyList.map(item => (
              <div>
                <FoodDetails expand="false" />
                <FoodDetails expand="true" name={item.name} restaurantName={item.restaurantName} restaurantId={item.restaurantId}
                description={item.description}
                price={item.price}
                rating={item.rating}
                count={item.count}
                oldPrice={item.oldPrice}
                img={item.img}
                />
              </div>
          ))}
          </div>
        </div>
        <div class="menu container">
          <div class="title">
            رستوران ها
          </div>
          <RestaurantList restaurants={restaurants}/>
        </div>
        <Footer/>
      </body>
    );
    }
  }

  componentDidMount() {
    fetch("http://localhost:8080/v1/restaurants")
      .then(res => res.json())
      .then(
        (result) => {
          console.log(result);
          this.setState({
            isLoaded: true,
            restaurants: result.restaurants,
            foodPartyList: result.foodparty,
            timeLeft: result.time
          });
        },
        // Note: it's important to handle errors here
        // instead of a catch() block so that we don't swallow
        // exceptions from actual bugs in components.
        (error) => {
          this.setState({
            isLoaded: true,
            error: error
          });
        }
      )
  }
}

export default Home;
