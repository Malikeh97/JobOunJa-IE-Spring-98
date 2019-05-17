import React from 'react';
import './navbar.css';
import {Link, NavLink, withRouter} from "react-router-dom";

const handleLogoutOrLogin = async (e, props) => {
    e.preventDefault();
    localStorage.clear();
    props.history.push("/login");
};

const NavBar = props => {
    const loggedInUsername = localStorage.getItem('username');
    const loggedInUserProfileURL = `/profile/${loggedInUsername}`;
    return (
        <nav className="navbar navbar-expand-sm bg-light">
            <div className="container">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="navbar-brand" to="/">
                            <img src={require("./logo v1.png")} alt="Logo" id="logo"/>
                        </Link>
                    </li>
                </ul>

                <ul className="navbar-nav" style={{float: 'left'}}>
                    <li className="nav-item">
                        <NavLink className="nav-link" to={loggedInUsername ? loggedInUserProfileURL : "/sign-up"}>
                            {loggedInUsername ? "حساب کاربری" : "ثبت نام"}
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" onClick={e => handleLogoutOrLogin(e, props)}>
                            {loggedInUsername ? "خروج" : "ورود"}
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    );
};


export default withRouter(NavBar);