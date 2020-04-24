import React from "react";
import Form from './Form';
import swal from "sweetalert";
import PropTypes from "prop-types";

class SearchForm extends Form {
  constructor(props) {
    super(props);
    this.state = {
      foodName: null,
      restaurantName: null
    };
  }

  mySubmitHandler = (event) => {
    let restaurantName = event.target.restaurantName.value;
    let foodName = event.target.foodName.value;
    event.preventDefault();
    if (restaurantName === "" && foodName === "") {
      swal({
        title: "هشدار",
        text: "نام رستوران و نام غذا نمی‌توانند هردو خالی باشند!",
        icon: "warning",
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
    const searchQuery = {
      restaurantName: "\"" + restaurantName + "\"",
      foodName: "\"" + foodName + "\""
    };
    Object.keys(searchQuery).forEach((key) => (searchQuery[key] === "\"\"") && delete searchQuery[key]);
    this.props.updateRestaurants(searchQuery);
  }

  render() {
    return (
        <div className="search centered-flex">
          <form className="form-inline justify-content-center shadow-box" onSubmit={this.mySubmitHandler}>
            <div className="form-group">
              <input type="text" className="form-control bg-light" name="foodName" placeholder="نـــام غـــذا"/>
              <input type="text" className="form-control bg-light" name="restaurantName"
                     placeholder="نـــام رســـتـــوران"/>
            </div>
            <button type="submit" className="btn btn-default">جســـت‌و‌جـــو</button>
          </form>
        </div>
    );
  }
}

SearchForm.propTypes = {
  updateRestaurants: PropTypes.func
};

export default SearchForm;