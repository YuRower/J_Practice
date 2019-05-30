import React from 'react';
import { withRouter } from "react-router-dom";
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem } from 'reactstrap';
import { Link } from 'react-router-dom';

class Topbar extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            userName: ''
        };
        this.state = { isOpen: false };
        this.toggle = this.toggle.bind(this);

    }


    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    componentWillMount() {
        const data = JSON.parse(localStorage.getItem('user'))
        console.log(data.result);
        this.setState({ userName: `${data.result.username} \u00A0\u00A0\u00A0` })
    }

    render() {
        return (


            <Navbar color="navbar navbar-expand-lg navbar-light bg-light" >
                <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
                <NavbarToggler onClick={this.toggle} />
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <h6 className="text-dark"> {this.state.userName}  </h6> 
                        </NavItem>
                        <NavItem>
                            <button className="btn float-right btn-danger" type="button" onClick={this.logout}>Logout</button>
                        </NavItem>
                    </Nav>
                </Collapse>
            </Navbar>
        );
    }

    logout = () => {
        localStorage.clear();
        this.props.history.push("/");
    }
}

export default withRouter(Topbar);