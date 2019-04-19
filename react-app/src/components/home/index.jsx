import React, {Component, Fragment} from 'react';
import './home.css';
import Header from "./header";
import {Form, FormGroup, Input} from "reactstrap";
import User from "./user";
import {getUsers} from "../../services/userService";

class Home extends Component {
    state = {
        users: [],
        projectSearchValue: '',
        userSearchValue: ''
    };

    async componentDidMount() {
        const { data: usersData } = await getUsers();
        this.setState({ users: usersData.data });
    }

    handleProjectSearchChange = e => {
        this.setState({ projectSearchValue: e.currentTarget.value });
    };

    handleProjectSearch = () => {
        console.log(this.state.projectSearchValue)
    };

    handleUserSearchChange = e => {
        this.setState({ userSearchValue: e.currentTarget.value });
    };

    handleOnUserClick = (userId) => {
        this.props.history.push(`/profile/${userId}`);
    };

    render() {
        const { projectSearchValue, userSearchValue } = this.state;

        return (
            <Fragment>
                <Header
                    searchValue={projectSearchValue}
                    onSearchInputChange={this.handleProjectSearchChange}
                    onSearchClicked={this.handleProjectSearch}
                />
                <div id="home-body" className="container-fluid">
                    <div className="row">
                        <div id="userList" className="col-md-3">
                            <Form>
                                <FormGroup id="userSearchTextField">
                                    <Input type="text" name="search" placeholder="جستجو نام کاربر"
                                           value={userSearchValue}
                                           onChange={this.handleUserSearchChange}/>
                                </FormGroup>
                            </Form>
                            {
                                this.state.users.map(user =>
                                    <User
                                        onUserClick={() => this.handleOnUserClick(user.id)}
                                        profilePictureURL={user.profilePictureURL}
                                        fullName={`${user.firstName} ${user.lastName}`}
                                        jobTitle={user.jobTitle}
                                    />
                                )
                            }
                        </div>
                        <div id="projectList" className="col-md-9">

                        </div>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default Home;