import React, {Component} from "react";
import Joi from 'joi-browser';
import './signup.css';
import {addSkill} from "../../services/userService";
import {toast} from "react-toastify";
import {registerUser} from "../../services/signupService";

class Signup extends Component {
    state = {
        inputs: {
            name: '',
            familyName: '',
            username: '',
            password: '',
            confirmPassword: '',
            jobTitle: '',
            image: '',
            bio: ''
        },
        errors: {}
    };

    schema = {
        name: Joi.string().required(),
        familyName: Joi.string().required(),
        username: Joi.string().required(),
        password: Joi.string().required(),
        confirmPassword: Joi.string().required(),
        jobTitle: Joi.string().required(),
        image: Joi.string().required(),
        bio: Joi.string().required()
    };

    handleInputChange = ({ currentTarget: input }) => {
        const inputs = { ...this.state.inputs };
        inputs[input.name] = input.value;
        this.setState({ inputs });
    };


    handleSubmit = async(e) => {
        try {
            e.preventDefault();

            const errors = this.validate();
            this.setState({ errors: errors || {} });
            if (errors) return;

            const data = { ...this.state.inputs };
            let resp = await registerUser(data);
            toast.success(resp)
        } catch(ex) {
            toast.error(ex.response.data.data)
        }

    };

    validate = () => {
        const result = Joi.validate(this.state.inputs, this.schema, {abortEarly: false});
        if (!result.error) return null;

        const errors = {};
        for (let item of result.error.details)
            errors[item.path[0]] = item.message;
        return errors;
    };


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
                                <input type="text" className="form-control" id="name" name="name"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.name && <div className="alert alert-danger">{this.state.errors.name}</div>}
                            </div>
                            <div className="form-group col-md-6">
                                <label htmlFor="family-name">نام خانوادگی:</label>
                                <input type="text" className="form-control" id="family-name" name="familyName"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.familyName && <div className="alert alert-danger">{this.state.errors.familyName}</div>}
                            </div>
                        </div>

                        <div className="row">
                            <div className="form-group col-md-6" id="username">
                                <label htmlFor="username-input">نام کاربری:</label>
                                <input type="text" className="form-control" id="username-input" name="username"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.username && <div className="alert alert-danger">{this.state.errors.username}</div>}
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="password">
                                <label htmlFor="password-input">رمزعبور:</label>
                                <input type="password" className="form-control" id="password-input" name="password"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.password && <div className="alert alert-danger">{this.state.errors.password}</div>}
                            </div>
                            <div className="form-group col-md-6" id="confirm-password">
                                <label htmlFor="confirm-password-input">تکرار رمزعبور:</label>
                                <input type="password" className="form-control" id="confirm-password-input"
                                       name="confirmPassword" onChange={this.handleInputChange}/>
                                {this.state.errors.confirmPassword && <div className="alert alert-danger">{this.state.errors.confirmPassword}</div>}
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="job-title">
                                <label htmlFor="job-title-input">عنوان شغلی:</label>
                                <input type="text" className="form-control" id="job-title-input" name="jobTitle"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.jobTitle && <div className="alert alert-danger">{this.state.errors.jobTitle}</div>}
                            </div>
                            <div className="form-group col-md-6" id="image">
                                <label htmlFor="image-input">عکس:</label>
                                <input type="text" className="form-control" id="image-input" name="image"
                                       onChange={this.handleInputChange}/>
                                {this.state.errors.image && <div className="alert alert-danger">{this.state.errors.image}</div>}
                            </div>
                        </div>
                        <div className="row">
                            <div className="form-group col-md-6" id="bio">
                                <label htmlFor="bio-input">بیوگرافی:</label>
                                <textarea className="form-control" id="bio-input" name="bio"
                                          onChange={this.handleInputChange}/>
                                {this.state.errors.bio && <div className="alert alert-danger">{this.state.errors.bio}</div>}
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary" id="signup-button"
                                onClick={this.handleSubmit}>ثبت نام
                        </button>
                    </form>
                </div>
            </div>);
    }
}

export default Signup;