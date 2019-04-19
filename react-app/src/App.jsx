import React, {Component} from 'react';
import {Route, Switch} from 'react-router-dom';

import NavBar from "./components/common/navbar";
import Profile from "./components/profile";
import Project from "./components/project";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.min.css';
import './App.css';


class App extends Component {
    render() {
        return (
            <React.Fragment>
                <ToastContainer/>
                <div className="rtl">
                    <NavBar/>
                    <main>
                        <Switch>
                            <Route path="/profile/:id" component={Profile}/>
                            <Route path="/projects/:id" component={Project}/>
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
