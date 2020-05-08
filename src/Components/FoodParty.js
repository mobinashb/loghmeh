import Timer from "react-compound-timer";
import {toPersianNum} from "../Utils/Utils";
import FoodDetails from "./FoodDetails";
import React from "react";
import axios from "axios";
import {SERVER_URI} from "../Constants/Constants";
import CartBasedComponent from "./CartBasedComponent";

class FoodParty extends CartBasedComponent {
  constructor(props) {
    super(props);
    this.state = {
      foodParty: [],
      partyRemainingTime: '',
      isLoaded: false
    }
  }

  render() {
    const {foodParty, partyRemainingTime, isLoaded} = this.state;
    return (
        <div className="menu">
          <div className="title">
            جشن غذا!
          </div>
          <div className="centered-flex">
            <div className="timer">
              {isLoaded &&
              <Timer
                  initialTime={parseInt(partyRemainingTime) * 1000}
                  direction="backward"
              >
                زمان باقی مانده: &nbsp;<b><Timer.Minutes
                  formatValue={value => toPersianNum(`${value}`)}/>:<Timer.Seconds
                  formatValue={value => toPersianNum(`${value}`)}/></b>
              </Timer>
              }
            </div>
          </div>
          <div className="scrolling-wrapper shadow-box">
            {foodParty.map(item => (
                <div className="card shadow-box" key={item.restaurantId+'-'+item.name}>
                  <FoodDetails whereAmI="foodparty"
                               name={item.name} restaurantName={item.restaurantName} restaurantId={item.restaurantId}
                               description={item.description}
                               price={item.price}
                               popularity={item.popularity}
                               count={item.count}
                               oldPrice={item.oldPrice}
                               showFunc={this.props.handleShow}
                               hideFunc={this.props.handleHide}
                               addToCart={this.props.addToCart}
                               image={item.image} />
                </div>
            ))}
          </div>
        </div>
    );
  }

  fetchFoodParty() {
    const jwt = localStorage.getItem("jwt");
    const options = {
      headers: {Authorization: `Bearer ${jwt}`}
    };
    axios.get(SERVER_URI + "/foodparty", options)
        .then(
            (response) => {
              this.setState({
                foodParty: response.data.foodparty,
                partyRemainingTime: response.data.remainingTime,
                isLoaded: true
              });
              this.props.setLoaded();
            },
            (error) => {
              this.handleError(error);
            }
        )
  }

  componentDidMount() {
    this.fetchFoodParty();
  }
}

export default FoodParty;

