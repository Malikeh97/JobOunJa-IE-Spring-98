import React from 'react';
import PropTypes from 'prop-types';
import {Button, Form, FormGroup, Input} from 'reactstrap';
import './header.css';

const Header = ({ searchValue, onSearchClicked }) => {
    return (
        <div id="home-head" className="container-fluid">
            <div id="header-text">جاب اونجا خوب است!</div>
            <div id="header-description">لورم ایپسوم متنی ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از
                طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در
            </div>
            <Form>
                <FormGroup id="searchTextField">
                    <Input type="text" name="search" placeholder="جستجو در جاب اونجا" value={searchValue}
                           onChange={(e) => searchValue = `${e.target.value}`}/>
                    <Button onClick={onSearchClicked}>جستجو</Button>
                </FormGroup>
            </Form>
        </div>
    );
};

Header.propTypes = {
    searchValue: PropTypes.string.isRequired,
    onSearchClicked: PropTypes.func.isRequired
};

export default Header;