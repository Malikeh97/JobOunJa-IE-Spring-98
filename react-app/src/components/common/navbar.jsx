import React from 'react';
import './navbar.css';
import {Link, NavLink} from "react-router-dom";
import {toast} from "react-toastify";
import {loginUser} from "../../services/loginService";

const handleSignout = async (e, props) => {
    try {
        e.preventDefault();
        localStorage.removeItem('jwtToken')
        props.history.push("/login");
    } catch (ex) {
        toast.error(ex.response.data.data)
    }

};

const NavBar = props => {

    const loggedInUserId = localStorage.getItem('userId');
    const loggedInUserProfileURL = `/profile/${loggedInUserId}`;
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
                        <NavLink className="nav-link" to={loggedInUserProfileURL}>حساب کاربری</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/logout" onClick={e => handleSignout(e, props)}>خروج</NavLink>
                    </li>
                </ul>
            </div>
        </nav>
    );
};


export default NavBar;