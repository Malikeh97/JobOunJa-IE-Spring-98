import React from 'react';
import PropTypes from 'prop-types';
import './required_skill.css';
import RequiredSkills from "./required_skills";

const RequiredSkill = ({ skill }) => {
    return(<div id="skill">
            <div className="skill">
                <span className="name">skill.name</span>
                <span className="point job-ounja-primary-color-text">
                         <div>skill.point</div>
                     </span>
            </div>
        </div>
    );
};

RequiredSkill.propType = {
    skill: PropTypes.string.isRequired
};

export default RequiredSkill;
