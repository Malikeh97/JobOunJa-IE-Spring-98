import React from 'react';
import {Dropdown, DropdownItem, DropdownMenu, DropdownToggle} from "reactstrap";

const SkillDropDown = ({ items, selectedValue, onItemSelect, dropdownIsOpen, toggle }) => {
    return (
        <React.Fragment>
            <div id="skills_text"><p>مهارت ها:</p></div>
            <div className="row" id="skill_container">
                <Dropdown isOpen={dropdownIsOpen} toggle={toggle}>
                    <DropdownToggle caret color="default">
                        {selectedValue}
                    </DropdownToggle>
                    <DropdownMenu
                        modifiers={{
                            setMaxHeight: {
                                enabled: true,
                                order: 890,
                                fn: (data) => {
                                    return {
                                        ...data,
                                        styles: {
                                            ...data.styles,
                                            overflow: 'auto',
                                            maxHeight: 100,
                                        },
                                    };
                                },
                            },
                        }}>
                        {
                            items.map(item => <DropdownItem key={item}
                                                            onClick={event => onItemSelect(event.target.innerText)}>{item}</DropdownItem>)
                        }
                    </DropdownMenu>
                </Dropdown>
                <button type="button" id="skill_btn">افزودن مهارت</button>
            </div>
        </React.Fragment>
    )
};

export default SkillDropDown;