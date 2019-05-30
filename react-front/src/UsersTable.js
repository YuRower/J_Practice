import React from 'react';
import { Table } from 'reactstrap';
import Moment from 'react-moment';
class UsersTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            lastUpdated: Date.now() - 1,
            users: []
        };

    }

    componentDidMount() {
        this.fetchData();
    }
    fetchData() {
        fetch('http://10.10.35.192:8080/Web_Services/rest/users')
            .then(response => response.json())
            .then(data => this.setState({
                users: data
            }))
            .catch(error => console.log('parsing failed', error));

    }
    render() {

        if (!this.state.users.user) {
            return <p>Loading ...</p>
        }
        console.log(this.state.users.user);
        const userList = this.state.users.user.map((user, key) =>
            <tr key={user.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{user.login}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{user.email}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{user.firstName}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{user.lastName}</td>
                <td style={{ whiteSpace: 'nowrap' }}>
                    <Moment format="YYYY/MM/DD">
                        {user.birthday}
                    </Moment>
                </td>
                <td style={{ whiteSpace: 'nowrap' }}>{user.roleId.name}</td>
                <td colSpan="2"><button type="button" className="btn btn-primary" size="sm" onClick={this.update.bind(this, user.login)}>Update</button>
                    <button type="button" size="sm" className="btn btn-danger" onClick={this.delete.bind(this, user.login)}>Delete</button></td>


            </tr>
        )
        return (
            <div>
                <h2 style={{ margin: 'margin-top' }}> User Details</h2>
                <div>
                    <button type="button" className="btn btn-success" onClick={this.create.bind(this)}>Create user</button>
                </div>

                <Table className="table table-striped">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col">Login</th>
                            <th scope="col">Email</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">Birthday</th>
                            <th scope="col">Role</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {userList}
                    </tbody>
                </Table>
            </div>
        )
    }


    delete(login) {
        if (window.confirm('are you sure want to delete?')) {
            fetch('http://10.10.35.192:8080/Web_Services/rest/users/user/' + login, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
            }
            ).then(response => {
                window.location.reload();
                //this.fetchData()
                window.location.reload();


            });
        }
    }
    update(login) {
        this.props.history.push('admin/create/' + login)
    }

    create() {
        this.props.history.push('/admin/create')
    }
}

export default UsersTable;