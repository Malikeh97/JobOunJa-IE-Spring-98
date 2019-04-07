import React, {Component} from 'react';
import './App.css';
import {Route, Switch} from 'react-router-dom';

import NavBar from "./components/common/navbar";
import Profile from "./components/profile";

class App extends Component {
    render() {
        return (
            <div className="rtl">
                <NavBar/>
                <main>
                    <div className="page-details">
                        <Switch>
                            <Route path="/profile/:id" component={Profile}/>
                        </Switch>
                    </div>
                </main>
                <footer>
                    <div>&copy; تمامی حقوق این سایت متعلق به جاب اونجا می باشد</div>
                </footer>
            </div>
        );
    }
}

export default App;
