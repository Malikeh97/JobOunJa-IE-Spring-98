import React from 'react';
import PropTypes from 'prop-types';
import './required_skills.css';
import RequiredSkill from "../common/required_skill";

const RequiredSkills = ({ skills }) => {
       return( <div id="skills">
               {
                   skills.map(skill => {
                       return (
                           <RequiredSkill key={skill.name}
                                  skill={skill}
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
