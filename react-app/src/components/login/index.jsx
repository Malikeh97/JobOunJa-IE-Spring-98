import React, {Component} from "react";
import './login.css';
import {toast} from "react-toastify";




class Login extends Component {
    render() {
        return (
            <div className="container-fluid" id="login">
                        <div className="well card card-body">
                            <h2 id="title">
                                 ورود
                            </h2>

                            <form>
                                <div className="row">
                                    <div className="form-group col-md-10" id="username">
                                        <label htmlFor="username-input">نام کاربری: </label>
                                        <input type="text" placeholder='نام کاربری' className="form-control" id="username-input"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="form-group col-md-10" id="password">
                                        <label htmlFor="password-input">رمزعبور: </label>
                                        <input type="password" placeholder='رمز عبور' className="form-control" id="password-input"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <button type="submit" className="btn btn-primary" id="login-button">ورود</button>
                                </div>

                            </form>
                        </div>

            </div>
        );
    }
}

export default Login;