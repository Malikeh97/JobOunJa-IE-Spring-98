import React, {Component} from 'react';
import './App.css';
import NavBar from "./components/common/navbar";
import {Route, Switch} from 'react-router-dom';

class App extends Component {
    render() {
        return (
            <div className="rtl">
                <NavBar/>
            </div>
        );
    }
}

export default App;
