import React, {Component} from "react";
import './project.css';
import ProjectInfo from "../common/project_info";
import RequiredSkills from "./required_skills";
import BidPlaceHolder from "./BidPlaceHolder";
import BidInfoText from "./BidInfoText";
import persianJs from "persianjs";

import deadline from '../../assets/deadline.svg';
import deadline_red from '../../assets/deadline_red.svg';
import money_bag from '../../assets/money_bag.svg';
import check_mark from '../../assets/check_mark.svg';

import {getProject, addBid} from '../../services/projectService';
import {toast} from "react-toastify";
import {formatNumber, calcTimeLeft, requestHandler} from "../../utilities";

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
        timeLeft: '',
        requiredSkills: [],
        bidAmount: ''
    };

    async componentDidMount() {
        await requestHandler(async () => {
            const {data: projectData} = await getProject(this.props.match.params.id);
            const {timeOver, text: timeLeft} = calcTimeLeft(projectData.data.project.deadline);
            this.setState({
                project: projectData.data.project,
                isBidAdded: projectData.data.bidAdded,
                requiredSkills: projectData.data.project.skills,
                timeOver, timeLeft
            });
            this.interval = setInterval(() => {
                const {timeOver, text: timeLeft} = calcTimeLeft(projectData.data.project.deadline);
                this.setState({timeOver, timeLeft});
                if (timeOver) clearInterval(this.interval);
            }, 1000);
        }, this.props);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    handleAddBid = async (e) => {
        e.preventDefault();
        await requestHandler(async () => {
            await addBid(this.state.project.id, {bidAmount: this.state.bidAmount});
            this.setState({isBidAdded: true});
            toast.success("مقدار پیشنهادی با موفقیت ثبت شد!")
        }, this.props);
    };

    updateBid = (e) => {
        this.setState({bidAmount: e.currentTarget.value});
    };

    render() {
        return (
            <div className="container-fluid" id="project">
                <div className="well card card-body">
                    <div className="row">
                        <div className="col-md-2 p-0">
                            <img src={this.state.project.imageUrl ? this.state.project.imageUrl : require("../../assets/target.png")}
                                 className="img-thumbnail over"// eslint-disable-line no-console
                                 alt="profile"
                                 id="profile-picture"/>
                        </div>
                        <div className="col-md-10">
                            <div id="project-title">{this.state.project.title}</div>

                            {
                                !this.state.timeOver && <ProjectInfo
                                    infoId="remaining-time"
                                    altIcon="deadline"
                                    iconSrc={deadline}
                                    featureText="زمان باقی مانده:"
                                    valueText={this.state.timeLeft}
                                    timeIsUp={this.state.timeOver}
                                />
                            }

                            {
                                this.state.timeOver && <ProjectInfo
                                    infoId="remaining-time"
                                    altIcon="deadliner_red"
                                    iconSrc={deadline_red}
                                    featureText=""
                                    valueText={this.state.timeLeft}
                                    timeIsUp={this.state.timeOver}
                                />
                            }


                            <ProjectInfo
                                infoId="budget"
                                altIcon="money-bag"
                                iconSrc={money_bag}
                                featureText="بودجه:"
                                valueText={persianJs(formatNumber(this.state.project.budget.toString())).englishNumber().toString() + ' تومان'}
                                timeIsUp={this.state.timeOver}
                            />

                            {
                                this.state.timeOver && this.state.project.winner && <ProjectInfo
                                    infoId="winner"
                                    altIcon="winner"
                                    iconSrc={check_mark}
                                    featureText="برنده:"
                                    valueText={this.state.project.winner.firstName + ' ' + this.state.project.winner.lastName}
                                    timeIsUp={this.state.timeOver}
                                />
                            }
                            {
                                this.state.timeOver && !this.state.project.winner && <ProjectInfo
                                    infoId="winner"
                                    altIcon="winner"
                                    iconSrc={check_mark}
                                    featureText=""
                                    valueText="پیشنهادی برنده نشده است"
                                    timeIsUp={this.state.timeOver}
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
                    {!this.state.isBidAdded && !this.state.timeOver &&
                    <div className="row" id="make-bid-row">
                        ثبت پیشنهاد
                    </div>
                    }
                    {
                        !this.state.isBidAdded && !this.state.timeOver &&
                        <BidPlaceHolder
                            bidAmount={this.state.bidAmount}
                            bidValueChanged={this.updateBid}
                            onClick={this.handleAddBid}
                        />
                    }
                    {
                        this.state.isBidAdded && <BidInfoText
                            BidState="Selected"
                            BidText="شما قبلا پیشنهاد خود را ثبت کرده اید"
                            color="green"
                            altText="check_mark.svg"
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

        );
    }
}

export default Project;