import React, {Component} from "react";
import './profile.css';
import Skill from "../common/skill";

class Profile extends Component {
    render() {
        return (
            <div className="container">
                <div className="row" id="title_bar">
                    <div id="profile-picture" className="col-md-3">
                        <img src="assets/alahazrat.jpg" className="img-thumbnail over" alt="Cinque Terre"/>
                    </div>
                    <svg height="30" width="150">
                        <polygon points="40,0 0,40 150,40 150,0" style={{ fill: "rgb(147, 216, 221)" }}/>
                        <polygon points="55,0 15,40 138,40 138,0" style={{ fill: "rgb(129, 199, 204)" }}/>
                        Sorry, your browser does not support inline SVG.
                    </svg>
                    <div id="user-details">
                        <div id="username">محمدرضا کیانی</div>
                        <div id="title">اعلی حضرت</div>
                    </div>
                </div>
                <div className="row" id="description"> لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با
                    استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و
                    برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد. کتابهای
                    زیادی در شصت و سه درصد گذشته، حال و آینده شناخت فراوان جامعه و متخصصان را می طلبد تا با نرم افزارها
                    شناخت بیشتری را برای طراحان رایانه ای علی الخصوص طراحان خلاقی و فرهنگ پیشرو در زبان فارسی ایجاد کرد.
                    در این صورت می توان امید داشت که تمام و دشواری موجود در ارائه راهکارها و شرایط سخت تایپ به پایان رسد
                    وزمان مورد نیاز شامل حروفچینی دستاوردهای اصلی و جوابگوی سوالات پیوسته اهل دنیای موجود طراحی اساسا
                    مورد استفاده قرار گیرد.
                </div>
                <div className="row" id="skill_row">
                    <div id="skills_text"><p>مهارت ها:</p></div>
                    <div className="row" id="skill_container">
                        <div className="dropdown">
                            <button className="btn btn-xs btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                    data-toggle="dropdown">
                                <span className="caret">-- انتخاب مهارت --</span>
                            </button>
                            {/*<ul className="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">*/}
                                {/*<li role="presentation"><a role="menuitem" tabIndex="-1" href="#">Unsorted <span*/}
                                    {/*className="badge">12</span></a></li>*/}
                                {/*<li role="presentation"><a role="menuitem" tabIndex="-1" href="#">Another action <span*/}
                                    {/*className="badge">42</span></a></li>*/}
                                {/*<li role="presentation"><a role="menuitem" tabIndex="-1" href="#">Something else*/}
                                    {/*here <span className="badge">42</span></a></li>*/}
                            {/*</ul>*/}
                        </div>
                        <button type="button" id="skill_btn">افزودن مهارت</button>
                    </div>
                </div>
                <div id="skills">
                    <div className="skill">
                        <span className="name">HTML</span>
                        <span className="point is_assigned">
                                <div>5</div>
                    </span>
                    </div>

                    <div className="skill">
                        <span className="name">CSS</span>
                        <span className="point is_assigned">
                                <div>3</div>
                    </span>
                    </div>

                    <Skill skill={{name: 'JavaScipt', point: 16}}/>

                    <Skill skill={{name: 'TypeScript', point: 0}}/>
                </div>
            </div>
        );
    }
}

export default Profile;