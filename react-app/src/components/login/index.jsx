import React, {Component} from "react";
import Joi from 'joi-browser'
import './login.css';
import {toast} from "react-toastify";
import {loginUser} from "../../services/loginService";




class Login extends Component {
    state = {
        inputs: {
            username: '',
            password: ''
        },
        errors: {}

    };

    schema = {
        username: Joi.string().required().error(e => {
            return {
                message: "نام کاربری اجباری است"
            }
        }),
        password: Joi.string().required().error(e => {
            return {
                message: "رمز عبور اجباری است"
            }
        })
    }

    handleInputChange = ({currentTarget: input}) => {
        const inputs = {...this.state.inputs};
        inputs[input.name] = input.value;
        this.setState({ inputs })
    };

    handleSubmit = async (e, props) => {
        try {
            e.preventDefault();
            let errors = this.validate();

            if (errors) {
                console.log(errors);
                Object.values(errors).forEach(err => toast.error(err));
                return;
            }

            const data = {...this.state.inputs};
            let {data: resp} = await loginUser(data);
            localStorage.setItem('jwtToken', resp.data);
            localStorage.setItem('username', this.state.inputs.username);
            props.history.push("/");
        } catch (ex) {
            toast.error(ex.response.data.data)
        }

    };

    validate = () => {
        const result = Joi.validate(this.state.inputs, this.schema, {abortEarly: false});
        console.log('error');
        if(!result.error) return null;

        const errors = {};
        for(let item of result.error.details)
            errors[item.path[0]] = item.message;
        return errors;
    };

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
                                        <input type="text" placeholder='نام کاربری' className="form-control" id="username-input" name="username" onChange={this.handleInputChange}/>
                                        {this.state.errors.username && <div className="alert alert-danger">{this.state.errors.username}</div>}
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="form-group col-md-10" id="password">
                                        <label htmlFor="password-input">رمزعبور: </label>
                                        <input type="password" placeholder='رمز عبور' className="form-control" id="password-input" name="password" onChange={this.handleInputChange}/>
                                        {this.state.errors.password && <div className="alert alert-danger">{this.state.errors.password}</div>}
                                    </div>
                                </div>
                                <div className="row">
                                    <button type="submit" className="btn btn-primary" id="login-button" onClick={e => this.handleSubmit(e, this.props)}>ورود</button>
                                </div>

                            </form>
                        </div>

            </div>
        );
    }
}

export default Login;