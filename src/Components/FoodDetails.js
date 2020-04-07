
import React from 'react';
import {toPersianNum} from '../Utils/Utils';
import star from '../Assets/star.png';
import Modal from "react-bootstrap/Modal";
import PropTypes from "prop-types";

class FoodDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      popularity: props.popularity,
      image: props.image,
      name: props.name,
      restaurantId: props.restaurantId,
      restaurantName: props.restaurantName,
      price: props.price,
      oldPrice: props.oldPrice,
      count: props.count,
      orderQuantity: 0,
      toShow: props.toShow,
      description: props.description
    };
  }

  showDetails() {
    this.setState({toShow: this.state.restaurantId.concat(this.state.name)});
    this.props.showFunc(this.state.restaurantId.concat(this.state.name));
  }

  hideDetails() {
    this.setState({toShow: null});
    this.props.hideFunc();
  }

  render() {
    const toShow = this.state.toShow;
    const id = this.state.restaurantId.concat(this.state.name);
    const whereAmI = this.props.whereAmI;
    const classForCard = (whereAmI === "menu") ? "col-sm-4" : "";
    return (
      <div className={classForCard}>
      {whereAmI === "foodparty" &&
      <div className="card-body">
        <div className="row">
          <div className="col-sm-2">
            <img src={this.state.image} alt={this.state.name}/>
          </div>
          <div className="col-sm-2">
            <div className="food-title">
              {this.state.name}
            </div>
            <span className="rating inline">
              {toPersianNum(this.state.popularity * 5)}
              <img src={star} alt=""/>
            </span>
            </div>
          </div>
          <ul className="price-old-new">
            {this.state.oldPrice !== undefined &&
            <li className="striked-through">
              {toPersianNum(this.state.oldPrice)}
            </li>
            }
            <li className="new-price">
              {toPersianNum(this.state.price)}
            </li>
          </ul>
          <button disabled className="in-stock">
            موجودی: {toPersianNum(this.state.count)}
          </button>
          &nbsp;&nbsp;&nbsp;
          <button className="cyan-btn" onClick={this.showDetails.bind(this)} disabled={this.state.count <= 0}>خرید</button>
          <hr className="dashed-top"/>
          {this.state.restaurantName}
        </div>
        }
        {whereAmI === "menu" &&
        <React.Fragment>
          <div className="card shadow-box">
            <div className="card-body">
              <img src={this.state.image} alt={this.state.name}/>
              <div className="row">
                <div className="food-title">
                  {this.state.name}
                </div>
                <span className="rating inline">
                {toPersianNum(this.state.popularity * 5)}
                  <img src={star} alt=""/>
                </span>
              </div>
            </div>
            <h6>{toPersianNum(this.state.price)} تومان</h6>
            <button className="small-btn yellow-btn" onClick={this.showDetails.bind(this)}>افزودن به سبد خرید</button>
          </div>
        </React.Fragment>
        }
        <Modal className="modal fade" role="dialog"
          show={toShow === id}
          onHide={this.hideDetails.bind(this)}>
          <Modal.Body>
          <div className="container-fluid card" id="foodDetails">
            <h3>
              {this.state.restaurantName}
            </h3>
            <div className="row">
              <div className="float-right col-sm-2">
                <img src={this.state.image} alt={this.state.name} className="shadow-box"/>
              </div>
              <div className="float-left col-sm-10">
                <div className="food-title">
                  {this.state.name}
                  <span className="rating inline">
                    {toPersianNum(this.state.popularity * 5)}
                    <img src={star} alt=""/>
                  </span>
                </div>
                <p>
                  {this.state.description}
                </p>
                <ul className="price-old-new">
                  &nbsp;&nbsp;
                  {this.state.oldPrice !== undefined &&
                  <li className="striked-through">
                    {toPersianNum(this.state.oldPrice)}
                  </li>
                  }
                  <li className="new-price">
                    {toPersianNum(this.state.price)} تومان
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <hr className="dashed-top"/>
          <div className="container">
          {this.state.oldPrice !== undefined && this.state.count > 0 &&
            <button disabled className="in-stock float-right">
              موجودی: {toPersianNum(this.state.count)}
            </button>
          }{this.state.oldPrice !== undefined && this.state.count <= 0 &&
            <button disabled className="in-stock float-right">
              ناموجود
            </button>
          }
            <button className="cyan-btn float-left" disabled={this.state.count <= 0 || this.state.orderQuantity < 1 || this.state.orderQuantity > this.state.count} onClick={this.addToCart.bind(this)}>افزودن به سبد خرید</button>
            <span className="plus-minus float-left">
              {this.state.orderQuantity === 0
                ? <i className="flaticon-minus grayed-out"/>
                : <i className="flaticon-minus" onClick={this.changeOrderQuantity.bind(this, -1)}/>
              }
              &nbsp;&nbsp;
              {toPersianNum(this.state.orderQuantity)}
              <i className="flaticon-plus" onClick={this.changeOrderQuantity.bind(this, +1)}/>
            </span>
          </div>
        </Modal.Body>
      </Modal>
    </div>
    );
  }

  changeOrderQuantity(num) {
    if (num === -1 && this.state.orderQuantity === 0) {
      return;
    }
    this.setState({
      orderQuantity: this.state.orderQuantity + num
    });
  }

  addToCart() {
    var food = {
      foodName: this.state.name,
      number: this.state.orderQuantity,
      restaurantId: this.state.restaurantId,
      isParty: (this.state.oldPrice !== undefined) ? 1 : 0,
      price: this.state.price
    };
    this.props.addToCart(food);
    this.hideDetails();
    this.setState({orderQuantity: 0});
  }
}

FoodDetails.propTypes = {
  popularity: PropTypes.number,
  image: PropTypes.string,
  name: PropTypes.string,
  restaurantId: PropTypes.string,
  restaurantName: PropTypes.string,
  price: PropTypes.number,
  oldPrice: PropTypes.number,
  count: PropTypes.number,
  toShow: PropTypes.string,
  description: PropTypes.string,
};

FoodDetails.defaultProps = {
  popularity: 0,
  image: '',
  name: '',
  restaurantId: '',
  restaurantName: '',
  price: 0,
  oldPrice: undefined,
  count: undefined,
  toShow: '',
  description: ''
};

export default FoodDetails;