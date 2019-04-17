import React, {Component} from "react";
import './project.css';
import ProjectInfo from "../common/project_info";
import RequiredSkills from "./required_skills";
import BidPlaceHolder from "./BidPlaceHolder";
import BidInfoText from "./BidInfoText";

import {getProjects, getProject, addBid} from '../../services/projectService';
import {toast} from "react-toastify";

class Project extends Component {
    state = {
        project: {
            id: '',
            title: '',
            description: '',
            skills: [],
            bids: [],
            budget: '',
            deadline:'',
            winner: null,
            imageUrl: ''

        },
        isBidAdded: false,
        requiredSkills: []
    };

    async componentDidMount() {
        const { data: projectData } = await getProject(this.props.match.params.id);
        console.log(projectData.data.project.skills)
        this.setState({ project: projectData.data.project, isBidAdded: projectData.data.bidAdded, requiredSkills: projectData.data.project.skills });
    }

    render() {
        return (
            <div className="container" id="project">
                    <div className="under" id="blue-section">
                    </div>
                    <div id="white-section">
                        <div className="container">

                            <div className="well card card-body">
                                <div className="row">
                                    <div className="col-md-2 p-0">
                                        <img src={require("../../assets/target.png")} className="img-thumbnail over" alt="default image"
                                             id="profile-picture"/>
                                    </div>
                                    <div className="col-md-10">
                                        <div id="project-title">پروژه طراحی سایت جاب اونجا</div>

                                         <ProjectInfo
                                             infoId = "remaining-time"
                                             altIcon= "deadline"
                                             iconSrc= "../../assets/deadline.svg"
                                             featureText = "زمان باقی مانده:"
                                             valueText = "۱۷ دقیقه و ۲۵ ثانیه"
                                             timeIsUp = {false}
                                         />

                                        <ProjectInfo
                                            infoId = "budget"
                                            altIcon= "money-bag"
                                            iconSrc= "../../assets/money-bag.svg"
                                            featureText = "بودجه:"
                                            valueText = "۲۵۰۰ تومان"
                                            timeIsUp = {false}
                                        />

                                        <ProjectInfo
                                            infoId = "winner"
                                            altIcon= "winner"
                                            iconSrc= "../../assets/check-mark.svg"
                                            featureText = "برنده:"
                                            valueText = "وحید محمدی"
                                            timeIsUp = {false}
                                        />

                                        <div id="description-static">
                                            توضیحات
                                        </div>
                                        <div id="description-text"> لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از
                                            صنعت چاپ و با
                                            استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و
                                            سطرآنچنان که لازم
                                            است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود
                                            ابزارهای کاربردی می
                                            باشد. کتابهای زیادی در شصت و سه درصد گذشته، حال و آینده شناخت فراوان جامعه و
                                            متخصصان را می
                                            طلبد تا با نرم افزارها شناخت بیشتری را برای طراحان رایانه ای علی الخصوص
                                            طراحان خلاقی و فرهنگ
                                            پیشرو در زبان فارسی ایجاد کرد.
                                        </div>
                                    </div>
                                </div>

                                <div className="row" id="skills-row">
                                    <div id="skills-static" className="job-ounja-primary-color-text">
                                        مهارت های لازم:
                                    </div>
                                    <RequiredSkills
                                        skills = {this.state.requiredSkills}
                                    />
                                </div>


                                {/*select bid section:*/}
                                {   !this.state.isBidAdded &&
                                    <div className="row" id="make-bid-row">
                                        ثبت پیشنهاد
                                    </div>
                                }
                                {
                                    !this.state.isBidAdded &&
                                     <BidPlaceHolder />

                                }
                                {
                                    this.state.isBidAdded && <BidInfoText
                                        BidState = "Selected"
                                        BidText = "شما قبلا پیشنهاد خود را ثبت کرده اید"
                                        color = "green"
                                        altText = "check-mark.svg"
                                    />
                                }
                                {
                                    this.state.isBidAdded && <BidInfoText
                                        BidState = "TimeOut"
                                        BidText = "مهلت ارسال پیشنهاد برای این پروژه به پایان رسیده است!"
                                        color = "red"
                                        altText = "danger.svg"
                                    />
                                }


                            </div>
                        </div>

                    </div>
            </div>
        );
    }
}

export default Project;