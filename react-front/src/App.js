import React, { Component } from 'react';
import './App.css';
import './Login.css';
import {Route, Switch} from 'react-router-dom';
import PrivateRoute from './PrivateRouter';
import Login from './Login';
import Admin from './Admin';
import User from './User';
import AccesDenied from './AccesDenied';
import NoMatch from './NoMatch';


class App extends Component {
  
  render() {
    return (
      <Switch>
      <Route exact path="/" component={Login}/>
      <PrivateRoute path='/admin' component={Admin}/>
      <PrivateRoute path='/user' component={User}/>
      <Route path="/403error" component={AccesDenied}/>
      <Route component={NoMatch}/>
      </Switch>
    );
  }
}

export default App;
