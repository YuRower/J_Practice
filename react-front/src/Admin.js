import React from 'react';
import UserTable from './UsersTable';
import Topbar from './Topbar';
import {Switch, Route} from 'react-router-dom';
import CreateUser from './CreateUser';
import NoMatch from './NoMatch';

class Admin extends React.Component {

    componentDidMount(){
        const user = JSON.parse(localStorage.getItem('user'))
        if (user.result.roleId.id !== 2) {
            this.props.history.push("/403error")
        }
    }

    render() {
        return (
            <div>
                <Topbar/>
                <div className="generic-container">
		<div className="center">

                        <Switch>
                    <Route exact path='/admin' component={UserTable}/>
                    <Route exact path='/admin/create' render={(props) => <CreateUser {...props} action={'Create'} />}/>
                    <Route path='/admin/create/:login' render={(props) => <CreateUser {...props} action={'Update'} />}/>
                    <Route component={NoMatch}/>
                    </Switch>
                </div>
                </div>
            </div>
                );
            }
        }

        
        
export default Admin;
