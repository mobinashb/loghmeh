import React from 'react';
import {Header, Footer} from './Utils'

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
    render() {
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
                        <img src="Assets/star.png"  alt=""/>
                    </span>
                </div>
                <h6>۲۹۰۰۰ تومان</h6>

                <button disabled class="in-stock">
                  موجودی: 3
                </button>
                <button disabled class="small-btn yellow-btn">ناموجود</button>
                <hr class="dashed-top"></hr>
                رستوران خامس
              </div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
              <div class="card shadow-box col-sm-2"><img src="Assets/pizza.jpg" alt="pizza"/>
                          <div class="food-title">
                              پیتزا نیمه اعلا
                              <span class="rating">
                                  ۴
                                  <img src="Assets/star.png"  alt=""/>
                              </span>
                          </div>
                          <h6>۲۹۰۰۰ تومان</h6>
                          <button disabled class="small-btn yellow-btn">ناموجود</button></div>
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
    }
}

export default Home;
