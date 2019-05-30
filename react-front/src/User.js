import React from 'react';

class User extends React.Component {

    constructor(props) {

        super(props);
        this.state = {
            user: [],
        };
    }

    componentDidMount() {
        const activeUser = JSON.parse(localStorage.getItem('user'))
        console.log(activeUser.result);
        this.setState({ user: activeUser.result })
    }

    logout = () => {
        localStorage.clear();
        this.props.history.push("/");
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="col-sm" />
                        <div className="col-sm">
                            <h1>Hello, {this.state.user.username}</h1>
                            <button className="btn float-right btn-danger" type="button" onClick={this.logout}>Logout</button>
                        </div>
                        <div className="col-sm" />
                    </div>
                </div>
            </div>
        );
    }
}

export default User;