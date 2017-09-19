import React, { Component } from 'react';
import '../css/App.css';
import Route from './Routes.js'
import CommitmentsTimeline from './CommitmentsTimeline.js'
import Header from './Header.js'
import moment from 'moment'

class App extends Component {
    constructor(props) {
        super(props)
        this.route = new Route()
        this.state = this.loadInitialState()
    }

    loadInitialState() {
        return {
            craftspeople: [{
                id: '',
                title: ''
            }]
        }
    }

    setProjects() {
        this.projects = this.route
            .allProjects()
            .then(projects => projects.map(project => ({
                id: project.id,
                name: project.name
            })))
    }

    componentWillMount() {
        this.route
            .allCraftspeople()
            .then(craftspeople => 
                this.setState((prevState, props) => ({
                    craftspeople: this.mapCraftspeople(craftspeople)
                }))
            )
    }

    mapCraftspeople(craftspeople) {
        return craftspeople.map(craftsperson => ({
            id: craftsperson.id,
            title: craftsperson.name
        }))
    }

    render() {
        return (
            <div>
                <Header />
                <div className="container-fluid commitments-board">
                    <CommitmentsTimeline craftspeople={this.state.craftspeople} />
                </div>
            </div>
       );
    }
}

export default App;
