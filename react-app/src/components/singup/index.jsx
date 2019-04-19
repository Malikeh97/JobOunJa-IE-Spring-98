import React, {Component} from "react";
import './signup.css';

class Signup extends Component {
    state = {};

    render() {
        return (
            <div className="container-fluid" id="sign-up">

                <div id="slideshow">
                    <div className="slide-wrapper">
                        <div className="slide"><img src={require("../../assets/1.jpg")}/></div>
                        <div className="slide"><img src={require("../../assets/2.jpg")}/></div>
                        <div className="slide"><img src={require("../../assets/3.jpg")}/></div>
                        <div className="slide"><img src={require("../../assets/4.jpg")}/></div>
                    </div>
                </div>

                <div className="well card card-body">
                    <h2 id="title">
                        ثبت نام
                    </h2>
                    <form action="">
                        <div className="row">
                            <div className="form-group col-md-6">
                                <label htmlFor="name">نام:</label>
                                <input type="text" className="form-control" id="name"/>
                            </div>
                            <div className="form-group col-md-6">
                                <label htmlFor="family-name">نام خانوادگی:</label>
                                <input type="text" className="form-control" id="family-name"/>
                            </div>
                        </div>

                        <div className="row">
                            <div className="form-group col-md-6" id="username">
                                <label htmlFor="username-input">نام کاربری:</label>
                                <input type="text" className="form-control" id="username-input"/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="password">
                                <label htmlFor="password-input">رمزعبور:</label>
                                <input type="password" className="form-control" id="password-input"/>
                            </div>
                            <div className="form-group col-md-6" id="confirm-password">
                                <label htmlFor="confirm-password-input">تکرار رمزعبور:</label>
                                <input type="password" className="form-control" id="confirm-password-input"/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="job-title">
                                <label htmlFor="job-title-input">عنوان شغلی:</label>
                                <input type="text" className="form-control" id="job-title-input"/>
                            </div>
                            <div className="form-group col-md-6" id="image">
                                <label htmlFor="image-input">عکس:</label>
                                <input type="text" className="form-control" id="image-input"/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="bio">
                                <label htmlFor="bio-input">بیوگرافی:</label>
                                <textarea className="form-control" id="bio-input"/>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary" id="signup-button">ثبت نام</button>
                    </form>
                </div>
            </div>);
    }
}

export default Signup;