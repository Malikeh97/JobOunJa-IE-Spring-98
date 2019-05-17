import React, {Component} from "react";
import './profile.css';
import Skill from "../common/skill";

import {addSkill, deleteSkill, endorse, getUser} from '../../services/userService';
import Dropdown from "../common/dropdown";
import {getSkills} from "../../services/skillsService";
import {toast} from "react-toastify";

class Profile extends Component {
    state = {
        user: {
            id: '',
            firstName: '',
            lastName: '',
            skills: [],
            profilePictureURL: ''
        },
        availableSkills: [],
        dropdownIsOpen: false,
        selectedNewSkill: ''
    };

    async componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.match.params.username !== this.props.match.params.username)
            await this.getData()
    }

    async componentDidMount() {
        await this.getData()
    }

    getData = async () => {
        const { data: userData } = await getUser(this.props.match.params.username);
        const availableSkills = [];
        const username = localStorage.getItem('username');
        if (username === this.props.match.params.username) {
            const { data: skillsData } = await getSkills();
            skillsData.data.forEach(skill => availableSkills.push(skill.name));
        }
        this.setState({ user: userData.data, availableSkills })
    };

    handleOnSkillClick = async (skill, isOwnSkill, isEndorsed) => {
        if (!isOwnSkill && !isEndorsed) {
            try {
                const { data } = await endorse(this.state.user.id, { skill });
                this.setState({ user: data.data })
            } catch (ex) {
                toast.error(ex.response.data.data)
            }
        } else if (isOwnSkill) {
            const { data: userData } = await deleteSkill(this.state.user.id, {skill});
            this.setState({ user: userData.data })
        }
    };

    handleDropdownSelect = (skill) => {
        this.setState({ selectedNewSkill: skill })
    };

    toggle = () => {
        this.setState(prevState => ({
            dropdownIsOpen: !prevState.dropdownIsOpen
        }));
    };

    handleDropdownButtonClick = async () => {
        try {
            const { data } = await addSkill(this.state.user.id, { skill: this.state.selectedNewSkill });
            let availableSkills = [...this.state.availableSkills];
            availableSkills = availableSkills.filter(skill => skill !== this.state.selectedNewSkill);
            this.setState({ user: data.data, selectedNewSkill: '', availableSkills });
        } catch (ex) {
            toast.error(ex.response.data.data)
        }
    };


    render() {
        const username = localStorage.getItem('username');
        const isOwnSkill = username === this.props.match.params.username;
        const { user, availableSkills, dropdownIsOpen, selectedNewSkill } = this.state;

        return (
            <div id="profile" className="container-fluid">
                <div className="row" id="title_bar">
                    <div id="profile-picture" className="col-md-3">
                        <img src={user.profilePictureURL} className="img-thumbnail over" alt="Profile"/>
                    </div>
                    <svg height="30" width="150">
                        <polygon points="40,0 0,40 150,40 150,0" style={{ fill: "rgb(147, 216, 221)" }}/>
                        <polygon points="55,0 15,40 138,40 138,0" style={{ fill: "rgb(129, 199, 204)" }}/>
                    </svg>
                    <div id="user-details">
                        <div id="username">{`${user.firstName} ${user.lastName}`}</div>
                        <div id="title">{user.jobTitle}</div>
                    </div>
                </div>
                <div className="row" id="description">
                    {user.bio}
                </div>
                {
                    isOwnSkill &&
                    <div className="row" id="skill_row">
                        <Dropdown
                            label="مهارت ها:"
                            buttonText="افزودن مهارت"
                            items={availableSkills}
                            defaultValue="-- انتخاب مهارت --"
                            selectedValue={selectedNewSkill}
                            dropdownIsOpen={dropdownIsOpen}
                            onItemSelect={this.handleDropdownSelect}
                            toggle={this.toggle}
                            onButtonClick={this.handleDropdownButtonClick}
                        />
                    </div>
                }
                <div id="skills">
                    {
                        user.skills &&
                        user.skills.map(skill => {
                            let isEndorsed = skill.endorsers && skill.endorsers.find(endorser => endorser === username);
                            return (
                                <Skill key={skill.name}
                                       skill={skill}
                                       isOwnSkill={isOwnSkill}
                                       isEndorsed={isEndorsed}
                                       onClick={() => this.handleOnSkillClick(skill.name, isOwnSkill, isEndorsed)}
                                />)
                        })
                    }
                </div>
            </div>
        );
    }
}

export default Profile;