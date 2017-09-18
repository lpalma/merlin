import React, { Component } from 'react';

class Header extends Component {
    render() {
        return (
            <div className="container-fluid">
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <a className="navbar-brand" href="#">
                        <img src="/images/codurance-logo.png" width="50" height="50" alt="codurance-logo" className="d-inline-block"/>
                        Merlin
                    </a>
                </nav>
            </div>
        )
    }
}

export default Header
