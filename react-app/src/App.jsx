import React, {Component} from 'react';
import {Route, Switch} from 'react-router-dom';
import {ToastContainer} from "react-toastify";

import NavBar from "./components/common/navbar";
import Profile from "./components/profile";
import Home from "./components/home";
import Project from "./components/project";
import Login from "./components/login";
import Signup from "./components/signup";
import 'react-toastify/dist/ReactToastify.min.css';
import './App.css';
import PrivateRoute from "./components/common/privateRoute";


class App extends Component {
    render() {
        return (
            <React.Fragment>
                <ToastContainer/>
                <div className="rtl">
                    <NavBar/>
                    <main>
                        <Switch>
                            <Route path="/sign-up" component={Signup}/>
                            <Route path="/login" component={Login}/>
                            <PrivateRoute path="/profile/:username" component={Profile}/>
                            <PrivateRoute path="/projects/:id" component={Project}/>
                            <PrivateRoute path="/" component={Home}/>
                        </Switch>
                    </main>
                    <footer>
                        <div>&copy; تمامی حقوق این سایت متعلق به جاب اونجا می باشد</div>
                    </footer>
                </div>
            </React.Fragment>
        );
    }
}

export default App;
