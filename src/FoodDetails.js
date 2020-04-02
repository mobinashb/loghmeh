
import React from 'react';
import {toPersianNum} from './Utils'
import star from './Assets/star.png';
import Modal from "react-bootstrap/Modal";

class FoodDetails extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rating: props.rating,
      img: props.img,
      name: props.name,
      restaurantId: props.restaurantId,
      restaurantName: props.restaurantName,
      price: props.price,
      oldPrice: props.oldPrice,
      count: props.count,
      orderQuantity: 0,
      show: false
    };
    this.handleShow = this.handleShow.bind(this);
    this.handleHide = this.handleHide.bind(this);
  }

  handleShow() {
    this.setState({show: true});
  }

  handleHide() {
    this.setState({show: false});
  }

  render() {
    if (this.props.expand === "false") return (
      <div class="card shadow-box col-sm-2">
        <div class="row">
          <div class="col-sm-2">
            <img src={this.state.img} alt={this.state.name}/>
          </div>
          <div class="col-sm-2">
            <div class="food-title">
              {this.state.name}
            </div>
            <span class="rating inline">
              ۴
              <img src={star} alt=""/>
            </span>
          </div>
        </div>
        <ul class="price-old-new">
          {this.state.oldPrice !== undefined &&
          <li h6 class="striked-through">
            {toPersianNum(this.state.oldPrice)}
          </li>
          }
          <li>
            {toPersianNum(this.state.price)}
          </li>
        </ul>
        <button disabled class="in-stock">
          موجودی: {toPersianNum(this.state.count)}
        </button>
        {this.state.count > 0 &&
        <button class="cyan-btn" onClick={() => this.handleShow()}>خرید</button>
        }
        {this.state.count === 0 &&
        <button disabled class="cyan-btn" onClick={() => this.handleShow()}>خرید</button>
        }
        <hr class="dashed-top"></hr>
        {this.state.restaurantName}
      </div>
    )
    else return (
      <Modal className="modal fade" role="dialog"
      show={this.state.show}
      onHide={this.handleHide}>
        <Modal.Body>
          <h3>
            {this.restaurantName}
          </h3>
          <div class="row">
            <div class="col-sm-6">
              <img src={this.state.img} alt={this.state.name} class="shadow-box"/>
            </div>
            <div class="col-sm-6">
              <div class="food-title">
                {this.state.name}
                <span class="rating inline">
                  {toPersianNum(this.state.rating)}
                  <img src={star} alt=""/>
                </span>
              </div>
              <p>
                {this.state.description}
              </p>
              <ul class="price-old-new">
                {this.state.oldPrice !== undefined &&
                <li h6 class="striked-through">
                  {toPersianNum(this.state.oldPrice)}
                </li>
                }
                <li>
                  {toPersianNum(this.state.price)} تومان
                </li>
              </ul>
            </div>
          </div>
          <hr class="dashed-top"></hr>
          <div class="container">
          {this.state.oldPrice !== undefined &&
            <button disabled class="in-stock float-right">
              موجودی: {toPersianNum(this.state.count)}
            </button>
          }
            <button class="cyan-btn float-left">افزودن به سبد خرید</button>
            <span class="plus-minus float-left">
              <i class="flaticon-minus" onClick={this.changeOrderQuantity.bind(this, -1)}></i>
              {toPersianNum(this.state.orderQuantity)}
              <i class="flaticon-plus" onClick={this.changeOrderQuantity.bind(this, +1)}></i>
            </span>
          </div>
        </Modal.Body>
      </Modal>
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
}

export default FoodDetails;