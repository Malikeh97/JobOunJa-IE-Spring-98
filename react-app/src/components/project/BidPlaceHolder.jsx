import React from "react";
import './BidPlaceHolder.css';

const BidPlaceHolder = ({ bidAmount, bidValueChanged, onClick}) => {

        return (
            <div className="row" id="bid-info-row">
                <div className="col-md-6">
                    <form>
                        <div className="input-group bid-input">
                             <input type="text" className="form-control" placeholder="پیشنهاد خود را وارد کنید" value={bidAmount} onChange={bidValueChanged} />
                                <div className="input-group-append">
                                    <span className="input-group-text">تومان</span>
                                </div>
                        </div>

                        <input type="submit" value="ارسال" className="btn btn-info" onClick={onClick}/>

                    </form>
                </div>
            </div>
        );

};



export default BidPlaceHolder;