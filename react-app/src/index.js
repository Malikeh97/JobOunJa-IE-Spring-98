import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

import 'bootstrap-v4-rtl/dist/css/bootstrap-rtl.min.css';
import 'vazir-font/dist/font-face.css';
import {BrowserRouter} from "react-router-dom";

localStorage.setItem('userId', 'c6a0536b-838a-4e94-9af7-fcdabfffb6e5');

ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>
    , document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
