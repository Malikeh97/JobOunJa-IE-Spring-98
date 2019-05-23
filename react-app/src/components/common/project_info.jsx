import React from 'react';
import PropTypes from 'prop-types';
import './project_info.css';

const ProjectInfo = ({infoId, altIcon, iconSrc, featureText, valueText, timeIsUp}) => {
    let featureClass = "static";
    let valueClass = "text";

    if (infoId === 'remaining-time' && timeIsUp) {
        featureClass += ' red';
        valueClass += ' red';
    } else if (infoId === 'budget') {
        featureClass += ' job-ounja-primary-color-text';
        valueClass += ' job-ounja-primary-color-text';
    } else if (infoId === 'winner') {
        featureClass += ' green';
        valueClass += ' green';
    }

    return (
        <div className="project-info" id={infoId}>
            <img src={iconSrc} alt={altIcon}/>
            {featureText && <span className={featureClass}>{featureText}</span>}
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
