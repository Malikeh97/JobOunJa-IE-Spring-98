import React from 'react';
import './navbar.css';
import {Link, NavLink} from "react-router-dom";

const NavBar = props => {
    return (
        <nav className="navbar navbar-expand-sm bg-light">
            <div className="container">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="navbar-brand" to="/">
                            <img src="assets/logo%20v1.png" alt="Logo" id="logo"/>
                        </Link>
                    </li>
                </ul>

                <ul className="navbar-nav" style={{float: 'left'}}>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/profile">حساب کاربری</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/logout">خروج</NavLink>
                    </li>
                </ul>
            </div>
        </nav>
    );
};

NavBar.propType = {};

export default NavBar;