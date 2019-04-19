import React, {Component} from "react";
import './project.css';
import ProjectInfo from "../common/project_info";
import RequiredSkills from "./required_skills";
import BidPlaceHolder from "./BidPlaceHolder";
import BidInfoText from "./BidInfoText";
import moment from 'moment';
import persianJs from "persianjs";

import deadline from '../../assets/deadline.svg';
import deadline_red from '../../assets/deadline_red.svg';
import money_bag from '../../assets/money_bag.svg';
import check_mark from '../../assets/check_mark.svg';

import {getProjects, getProject, addBid} from '../../services/projectService';
import {toast} from "react-toastify";


function timeLeft(deadline) {
    deadline = moment(deadline)
    let currentTime = moment();
    let diff = deadline.diff(currentTime);
    console.log('test: ' + diff.toString());
    if (diff <= 0)
        return 'مهلت تمام شده';

    let x = [];

    let duration = moment.duration(diff);
    let seconds = duration.seconds();
    x.push({value: seconds, text: 'ثانیه'});

    let minutes = duration.minutes();
    x.push({value: minutes, text: 'دقیقه'});

    let hours = duration.hours();
    x.push({value: hours, text: 'ساعت'});

    let days = duration.days();
    x.push({value: days, text: 'روز'});

    let months = duration.months();
    x.push({value: months, text: 'ماه'});

    let years = duration.years();
    x.push({value: years, text: 'سال'});

    let timeLeft = '';

    for (let i = x.length - 1; i >= 0; i--) {
        let item = x[i];
        if (item.value > 0)
            timeLeft += `${persianJs(item.value).englishNumber()} ${item.text}`;
        if (i != 0)
            timeLeft += ' و '
    }
    return timeLeft;

}


class Project extends Component {
    state = {
        project: {
            id: '',
            title: '',
            description: '',
            skills: [],
            bids: [],
            budget: '0',
            deadline: '',
            winner: null,
            imageUrl: ''

        },
        isBidAdded: false,
        timeOver: false,
        requiredSkills: []
    };

    async componentDidMount() {
        const {data: projectData} = await getProject(this.props.match.params.id);
        console.log(projectData.data.project.skills)
        this.setState({
            project: projectData.data.project,
            isBidAdded: projectData.data.bidAdded,
            requiredSkills: projectData.data.project.skills
        });
    }

    render() {
        this.state.timeOver = (timeLeft(this.state.project.deadline) === 'مهلت تمام شده');
        return (
            <div className="container" id="project">
                <div className="under" id="blue-section">
                </div>
                <div id="white-section">
                    <div className="container">

                        <div className="well card card-body">
                            <div className="row">
                                <div className="col-md-2 p-0">
                                    <img src={require("../../assets/target.png")} className="img-thumbnail over"
                                         alt="default image"
                                         id="profile-picture"/>
                                </div>
                                <div className="col-md-10">
                                    <div id="project-title">پروژه طراحی سایت جاب اونجا</div>

                                    {
                                        !this.state.timeOver &&  <ProjectInfo
                                            infoId="remaining-time"
                                            altIcon="deadline"
                                            iconSrc={deadline}
                                            featureText="زمان باقی مانده:"
                                            valueText={timeLeft(this.state.project.deadline)}
                                            timeIsUp={!this.state.timeOver}
                                        />
                                    }

                                    {
                                        this.state.timeOver &&  <ProjectInfo
                                            infoId="remaining-time"
                                            altIcon="deadliner_red"
                                            iconSrc={deadline_red}
                                            featureText=""
                                            valueText=""
                                            timeIsUp={this.state.timeOver}
                                        />
                                    }


                                    <ProjectInfo
                                        infoId="budget"
                                        altIcon="money-bag"
                                        iconSrc={money_bag}
                                        featureText="بودجه:"
                                        valueText={persianJs(this.state.project.budget.toString(10)).englishNumber().toString() + ' تومان'}
                                        timeIsUp={false}
                                    />

                                    {
                                        (this.state.project.winner !== null) && <ProjectInfo
                                            infoId="winner"
                                            altIcon="winner"
                                            iconSrc={check_mark}
                                            featureText="برنده:"
                                            valueText={this.state.project.winner.firstName + ' ' + this.state.project.winner.lastName}
                                            timeIsUp={false}
                                        />
                                    }

                                    <div id="description-static">
                                        توضیحات
                                    </div>
                                    <div id="description-text">
                                        {this.state.project.description}
                                    </div>
                                </div>
                            </div>


                            {/*Required skills section: */}
                            <div className="row" id="skills-row">
                                <div id="skills-static" className="job-ounja-primary-color-text">
                                    مهارت های لازم:
                                </div>
                                <RequiredSkills
                                    skills={this.state.requiredSkills}
                                />
                            </div>


                            {/*select bid section:*/}
                            {!this.state.isBidAdded &&
                            <div className="row" id="make-bid-row">
                                ثبت پیشنهاد
                            </div>
                            }
                            {
                                !this.state.isBidAdded && !this.state.timeOver &&
                                <BidPlaceHolder/>
                            }
                            {
                                this.state.isBidAdded && <BidInfoText
                                    BidState="Selected"
                                    BidText="شما قبلا پیشنهاد خود را ثبت کرده اید"
                                    color="green"
                                    altText="check-mark.svg"
                                />
                            }
                            {
                                this.state.timeOver && !this.state.isBidAdded && <BidInfoText
                                    BidState="TimeOut"
                                    BidText="مهلت ارسال پیشنهاد برای این پروژه به پایان رسیده است!"
                                    color="red"
                                    altText="danger.svg"
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