import React from 'react';
import PropTypes from 'prop-types';
import './skill.css';

const Skill = ({ skill, isOwnSkill, isEndorsed, onClick }) => {
    let skillClass = 'point default';
    let skillButtonText = skill.point;

    if (isOwnSkill && skill.point === 0) {
        skillClass = 'point not-assigned';
        skillButtonText = '-';
    } else if (!isOwnSkill && !isEndorsed) {
        skillClass = 'point not-endorsed';
        skillButtonText = '+';
    }
    return (
        <div className="skill">
            <span className="name">{skill.name}</span>
            <span className={skillClass} onClick={onClick}>
                <div>{skillButtonText}</div>
            </span>
        </div>
    );
};

Skill.propType = {
    skill: PropTypes.object.isRequired,
    isOwnSkill: PropTypes.bool.isRequired,
    isEndorsed: PropTypes.bool.isRequired,
    onClick: PropTypes.func.isRequired
};

export default Skill;