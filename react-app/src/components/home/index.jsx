import React, {Component, Fragment} from 'react';
import './home.css';
import Header from "./header";
import {Form, FormGroup, Input} from "reactstrap";
import User from "./user";
import {getUsers} from "../../services/userService";
import Project from "./project";
import {getProjects} from "../../services/projectService";

class Home extends Component {
    state = {
        users: [],
        projects: [],
        projectSearchValue: '',
        userSearchValue: '',
        typing: false,
        typingTimeout: 0
    };

    async componentDidMount() {
        const {data: usersData} = await getUsers();
        const {data: projectsData} = await getProjects();
        this.setState({users: usersData.data, projects: projectsData.data});
    }

    handleProjectSearchChange = e => {
        this.setState({projectSearchValue: e.currentTarget.value});
    };

    handleProjectSearch = async () => {
        console.log(this.state.projectSearchValue)
        const {data: projectsData} = await getProjects(this.state.projectSearchValue);
        this.setState({projects: projectsData.data})
    };

    handleUserSearchChange = e => {

        if (this.state.typingTimeout) {
            clearTimeout(this.state.typingTimeout);
        }

        this.setState({
            userSearchValue: e.target.value,
            typing: false,
            typingTimeout: setTimeout(async () => {
                console.log(this.state.userSearchValue);
                const {data: usersData} = await getUsers(this.state.userSearchValue);
                this.setState({users: usersData.data})
            }, 700)
        });
        // this.setState({ userSearchValue: e.currentTarget.value });
    };

    handleOnUserClick = userId => {
        this.props.history.push(`/profile/${userId}`);
    };

    handleOnProjectClick = projectId => {
        this.props.history.push(`/projects/${projectId}`);
    };

    render() {
        const {projectSearchValue, userSearchValue} = this.state;

        return (
            <Fragment>
                <Header
                    searchValue={projectSearchValue}
                    onSearchInputChange={this.handleProjectSearchChange}
                    onSearchClicked={this.handleProjectSearch}
                />
                <div id="home-body" className="container-fluid">
                    <div className="row">
                        <div id="usersList" className="col-md-3">
                            <Form id="usersForm">
                                <FormGroup id="userSearchTextField">
                                    <Input type="text" name="search" placeholder="جستجو نام کاربر"
                                           value={userSearchValue}
                                           onChange={this.handleUserSearchChange}/>
                                </FormGroup>
                            </Form>
                            <div id="users">
                                {
                                    this.state.users.map(user =>
                                        <User
                                            key={user.id}
                                            onUserClick={() => this.handleOnUserClick(user.username)}
                                            profilePictureURL={user.profilePictureURL}
                                            fullName={`${user.firstName} ${user.lastName}`}
                                            jobTitle={user.jobTitle}
                                        />
                                    )
                                }
                            </div>
                        </div>
                        <div id="projectList" className="col-md-9">
                            {
                                this.state.projects.map(project =>
                                    <Project
                                        key={project.id}
                                        project={project}
                                        onProjectClick={() => this.handleOnProjectClick(project.id)}
                                    />
                                )
                            }
                        </div>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default Home;