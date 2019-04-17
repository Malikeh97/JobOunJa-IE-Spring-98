import React, {Component} from "react";
import './BidPlaceHolder.css';


class BidPlaceHolder extends Component {
    render() {
        return (
            <div>
                <div className="col-md-6">
                    <form>
                        <div className="input-group bid-input">
                             <input type="text" className="form-control" placeholder="پیشنهاد خود را وارد کنید"/>
                                <div className="input-group-append">
                                    <span className="input-group-text">تومان</span>
                                </div>
                        </div>

                        <input type="submit" value="ارسال" className="btn btn-info"/>
                    </form>
                </div>
            </div>
        );
    }

}

export default BidPlaceHolder;