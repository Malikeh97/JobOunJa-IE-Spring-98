import React from 'react';
import './project.css';
import persianJs from 'persianjs';
import {calcTimeLeft, formatNumber} from "../../utilities";

const Project = ({ project, onProjectClick }) => {
    const budget = persianJs(`${project.budget ? formatNumber(project.budget) : 0}`).englishNumber().toString();
    const { timeOver, text: timeLeft } = calcTimeLeft(project.deadline);
    return (
        <div className="row project" onClick={onProjectClick}>
            <img src={project.imageURL ? project.imageURL : require('./project.png')} alt="project"/>
            <div className="details">
                <div className="title">
                    <span>{project.title}</span>
                    <span className={timeOver ? 'time-over' : ''}>{timeOver ? timeLeft : `زمان باقی مانده: ${timeLeft}`}</span>
                </div>
                <div className="description">{project.description}</div>
                <div className="budget">بودجه: {budget} تومان</div>
                <div className="required-skills">مهارت ها: {
                    project.skills.map(skill => <span key={skill.name}>{skill.name}</span>)
                }</div>
            </div>
        </div>
    );
};

export default Project;