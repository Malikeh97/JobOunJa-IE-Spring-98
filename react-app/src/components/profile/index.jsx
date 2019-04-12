import React, {Component} from "react";
import './profile.css';
import Skill from "../common/skill";

import {addSkill, getUser} from '../../services/userService';
import Dropdown from "../common/dropdown";
import {getSkills} from "../../services/skillsService";

class Profile extends Component {
    state = {
        user: {
            id: '',
            firstName: '',
            lastName: '',
            skills: [],
            profilePictureURL: 'https://cdn.guidingtech.com/media/assets/WordPress-Import/2012/10/Smiley-Thumbnail.png'
        },
        availableSkills: [],
        dropdownIsOpen: false,
        selectedNewSkill: '-- انتخاب مهارت --'
    };

    async componentDidMount() {
        const { data: userData } = await getUser(this.props.match.params.id);
        const { data: skillsData } = await getSkills();
        const availableSkills = [];
        skillsData.data.forEach(skill => availableSkills.push(skill.name));
        this.setState({ user: userData.data, availableSkills })
    }

    handleOnSkillClick = (isOwnSkill, isEndorsed) => {
        if (!isOwnSkill && !isEndorsed) {
            // send endorse request
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
        const { data } = await addSkill(this.state.user.id, { skill: this.state.selectedNewSkill });
        this.setState({ user: data.data })
    };


    render() {
        let userId = localStorage.getItem('userId');
        const isOwnSkill = userId === this.props.match.params.id;
        const { user, availableSkills, dropdownIsOpen, selectedNewSkill } = this.state;

        return (
            <div className="container">
                <div className="row" id="title_bar">
                    <div id="profile-picture" className="col-md-3">
                        <img src={user.profilePictureURL} className="img-thumbnail over" alt="Cinque Terre"/>
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
                        user.skills.map(skill => {
                            let isEndorsed = skill.endorsers && skill.endorsers.find(endorser => endorser === userId);
                            return (
                                <Skill key={skill.name}
                                       skill={skill}
                                       isOwnSkill={isOwnSkill}
                                       onClick={() => this.handleOnSkillClick(isOwnSkill, isEndorsed)}
                                />)
                        })
                    }
                </div>
            </div>
        );
    }
}

export default Profile;