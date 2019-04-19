import React, {Component} from "react";
import PropTypes from 'prop-types';
import './BidInfoText.css';


const BidInfoText = ({ BidState, BidText, color, altText }) => {
    let textColor = "text ";
    textColor = textColor + color;
    return (
        <div className="row" id="bid-info-row">
            <div className="col-md-6" id="bid">
                <img src={require( "../../assets/" + (BidState === 'Selected'? "check_mark.svg" : "danger.svg"))} alt={altText}/>
                <span className={textColor}>{BidText}</span>
            </div>
        </div>
    );

};

BidInfoText.propType = {
    BidState: PropTypes.string.isRequired,
    BidText: PropTypes.string.isRequired,
    color: PropTypes.string.isRequired,
    altText: PropTypes.string.isRequired
};

export default BidInfoText;
