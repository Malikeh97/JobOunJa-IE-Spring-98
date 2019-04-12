import React from 'react';
import PropTypes from 'prop-types';
import {Dropdown as BSDropdown, DropdownItem, DropdownMenu, DropdownToggle} from "reactstrap";
import './dropdown.css';

const Dropdown = ({ label, buttonText, items, defaultValue, selectedValue, onItemSelect, dropdownIsOpen, toggle, onButtonClick }) => {
    return (
        <React.Fragment>
            <div id="dropdown-text"><p>{label}</p></div>
            <div className="row" id="dropdown-container">
                <BSDropdown isOpen={dropdownIsOpen} toggle={toggle}>
                    <DropdownToggle caret color="default">
                        {selectedValue !== '' ? selectedValue : defaultValue}
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
                </BSDropdown>
                <button type="button" id="dropdown-button" onClick={onButtonClick}>{buttonText}</button>
            </div>
        </React.Fragment>
    )
};

Dropdown.defaultProps = {
    defaultValue: 'selected value'
};

Dropdown.propTypes = {
    label: PropTypes.string.isRequired,
    buttonText: PropTypes.string.isRequired,
    items: PropTypes.array.isRequired,
    defaultValue: PropTypes.string,
    selectedValue: PropTypes.string.isRequired,
    dropdownIsOpen: PropTypes.bool.isRequired,
    onItemSelect: PropTypes.func.isRequired,
    toggle: PropTypes.func.isRequired,
    onButtonClick: PropTypes.func.isRequired
}

export default Dropdown;