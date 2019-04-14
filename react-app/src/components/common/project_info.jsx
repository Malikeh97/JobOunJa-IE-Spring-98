import React from 'react';
import PropTypes from 'prop-types';
import './project_info.css';

const ProjectInfo = ({ infoId, altIcon, iconSrc, featureText, valueText, timeIsUp }) => {
    let featureClass = "static"
    let valueClass = "text"

    if (timeIsUp) {
        featureClass = featureClass + ' red'
    } else if (infoId === 'budget') {
        featureClass = featureClass + ' job-ounja-primary-color-text'
        valueClass = featureClass + ' job-ounja-primary-color-text'
    }
    return (
        <div id={infoId}>
            <img src={require( "../../assets/" + (infoId === 'budget'? "money-bag.svg" : (infoId === 'winner'? "check-mark.svg" : "deadline.svg" )  ))} alt={altIcon}/>
            <span className={featureClass}>{featureText}</span>
            <span className={valueClass}> {valueText} </span>
        </div>
    );
};

ProjectInfo.propType = {
    infoId: PropTypes.string.isRequired,
    altIcon: PropTypes.string.isRequired,
    iconSrc: PropTypes.string.isRequired,
    featureText: PropTypes.string.isRequired,
    valueText: PropTypes.string.isRequired,
    timeIsUp: PropTypes.bool.isRequired
};

export default ProjectInfo;
