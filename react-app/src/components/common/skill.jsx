import React from 'react';
import PropTypes from 'prop-types';
import './skill.css';

const Skill = ({ skill }) => {
    return (
        <div className="skill">
            <span className="name">{skill.name}</span>
            <span className={skill.point === 0 ? "point not_assigned" : "point is_assigned"}>
                <div>{skill.point === 0 ? '-' : skill.point}</div>
            </span>
        </div>
    );
};

Skill.propType = {
    skill: PropTypes.object.isRequired
}

export default Skill;