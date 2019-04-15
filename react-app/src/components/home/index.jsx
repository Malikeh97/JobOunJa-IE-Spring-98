import React, {Component, Fragment} from 'react';
import './home.css';
import Header from "./header";

class Home extends Component {
    state = {
        searchValue: ''
    };

    handleSearch = () => {
        console.log(this.state.searchValue)
    };

    render() {
        const { searchValue } = this.state;

        return (
            <Fragment>
                <Header
                    searchValue={searchValue}
                    onSearchClicked={this.handleSearch}
                />
                <div id="home-body" className="container-fluid">

                </div>
            </Fragment>
        );
    }
}

export default Home;