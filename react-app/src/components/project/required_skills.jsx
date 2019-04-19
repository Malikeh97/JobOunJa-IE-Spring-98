import React from 'react';
import PropTypes from 'prop-types';
import './required_skills.css';
import Skill from "../common/skill";

const RequiredSkills = ({ skills }) => {
       return( <div id="skills">
               {
                   skills.map(skill => {
                       return (
                           <Skill key={skill.name}
                                  skill={skill}
                                  isOwnSkill={false}
                                  isEndorsed={true}
                           />)
                   })
               }
                </div>
        );
};

RequiredSkills.propType = {
    skills: PropTypes.string.isRequired
};

export default RequiredSkills;
