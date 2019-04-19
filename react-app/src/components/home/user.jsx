import React from 'react';
import PropTypes from 'prop-types';
import './user.css';

const User = ({ onUserClick, profilePictureURL, fullName, jobTitle }) => {
    return (
        <div className="user" onClick={onUserClick}>
            <div className="image">
                <img src={profilePictureURL} alt={fullName}/>
            </div>
            <div className="details">
              <div className="full-name">{fullName}</div>
              <div className="job-title">{jobTitle}</div>
            </div>
        </div>
    );
};

User.propType = {
    onUserClick: PropTypes.func.isRequired,
    profilePictureURL: PropTypes.string.isRequired,
    fullName: PropTypes.string.isRequired,
    jobTitle: PropTypes.string.isRequired
};

export default User;