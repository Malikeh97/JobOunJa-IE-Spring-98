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
        valueClass = valueClass + ' job-ounja-primary-color-text'
    } else if (infoId === 'winner') {
        featureClass = featureClass + ' green'
        valueClass = valueClass + ' green'
    }
    return (
        <div id={infoId}>
            <img src={require( "../../assets/" + (infoId === 'budget'? "money-bag.svg" : (infoId === 'winner'? "check-mark.svg" : (timeIsUp? "deadline-red.svg" : "deadline.svg" ) )  ))} alt={altIcon}/>
            {timeIsUp?  <span className={featureClass}>مهلت تمام شده</span> : <span className={featureClass}>{featureText}</span>}
            {!timeIsUp && <span className={valueClass}> {valueText} </span>}
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
