import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { Button, FormGroup, Label } from 'reactstrap';
import './CreateUser.css'
class CreateUser extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            disabled: true,
            serverError: false,
            id: '',
            user: [],
            errorMsg: '',
            login: '',
            password: '',
            email: '',
            firstName: '',
            lastName: '',
            birthday: '',
            roleId:  {
                id: '1',
name: 'USER'
            }

        }
        this.onChangeRole = this.onChangeRole.bind(this);
    }

    handleError(response) {

        if (!response.ok) {
            throw response;
        }
        return response;
    }

    componentWillMount() {
        if (this.props.action === 'Create') {
            this.setState( {disabled: !this.state.disabled} )
        }

        console.log(this.props.match.params.login)
        if (this.props.match.params.login) {
            const login = this.props.match.params.login
            this.setState({ login: login })
            fetch('http://10.10.35.192:8080/Web_Services/rest/users/user/' + login)
                .then(this.handleError)
                .then(response => response.json())
                .then(data => {
                    this.setState({
                        serverError: false,
                        data: data,
                        login: data.user.login,
                        password: data.user.password,
                        email: data.user.email,
                        firstName: data.user.firstName,
                        lastName: data.user.lastName,
                        birthday: data.user.birthday,
                        roleId:   data.user.roleId
                    })
                    console.log(this.state.data.user)
                })
                .catch(err => {
                    //err.text().then(error => {
                    console.error(err);
                    // const parsered = JSON.parse(error);
                    this.setState({
                        //  errorMsg: parsered.errorMessage,
                        serverError: true
                    });
                })
            //     })
        }
    }

    updateUser(values) {
        console.log(values);
        values.roleId = this.state.roleId;

        fetch('http://10.10.35.192:8080/Web_Services/rest/users/user/' + this.state.login, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        })
            .then(response => {
                if (!response.ok) {
                    throw response
                }
            })
            .then(response => {
                console.log(response);
               this.props.history.push("/admin")
            })
            .catch(err => {
                console.log(err)
                //err.text().then(error => {
                   // const parsered = JSON.parse(error);
                 /*   this.setState({
                        errorMsg: parsered.errorMessage,
                    });
                })*/
            })
        console.log(values)

    }
    onChangeRole(event){
        var roleid;
        console.log( event.target.value)
        if (event.target.value === "USER"){
            console.log(1);
            roleid=1;
        }else {
            console.log(2);
            roleid=2;
        }
        var role = {
            id: roleid,
            name: event.target.value
        }
        console.log(role);

    
      this.setState({roleId:role});
      console.log(this.state.roleId)

    }



    createUser(values) {
    
        console.log(values);
        values.roleId = this.state.roleId;

        fetch('http://10.10.35.192:8080/Web_Services/rest/users/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(values)
        })
            .then(response => {
                if (!response.ok) { throw response }
            })
            .then(response => {
                console.log(response);

                this.props.history.push("/admin")
            })
            .catch(err => {
                err.text().then(error => {
                    const parsered = JSON.parse(error);
                    this.setState({ errorMsg: parsered.errorMessage });
                })
            })
            console.log(values)

    }

    goBack() {
        this.props.history.push("/admin")
    }


    render() {
        if (!this.state.serverError) {
            return (
                <div>
                    <Formik
                        initialValues={{
                            login: this.state.login,
                            password: this.state.password,
                            email: this.state.email,
                            firstName: this.state.firstName,
                            lastName: this.state.lastName,
                            birthday: this.state.birthday,
                            roleId:  {
                                id: '1',
                name: 'USER'
                            }
                        }}
                        validationSchema={CreateSchema}
                        enableReinitialize={true}
                        onSubmit={values => {
                            if (this.props.action === 'Create') {
                                this.createUser(values)

                            } else {
                                this.updateUser(values)
                                this.setState( {disabled: !this.state.disabled} )
                            }
                        }}>
                        <Form>
                            <div className="container">
                                <div className="row">
                                    <div className="col-md-6 user-container">                                            <div>
                                        <h2>{this.props.action} user</h2>
                                    </div>
                                        <p className="danger"> {this.state.errorMsg} </p>
                                        <FormGroup>
                                            <Label for="login">Login</Label><br />
                                            <Field  disabled = {(this.state.disabled)? "disabled" : ""} name="login" id="login" />
                                            <p className="danger"><ErrorMessage name="login" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="password">Password</Label><br />
                                            <Field name="password" id="password" />
                                            <p className="danger"><ErrorMessage name="password" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="email">Email</Label><br />
                                            <Field name="email" type="email" id="email" />
                                            <p className="danger"><ErrorMessage name="email" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="firstName">firstName</Label><br />
                                            <Field name="firstName" id="firstName" />
                                            <p className="danger"><ErrorMessage name="firstName" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="lastName">LastName</Label><br />
                                            <Field name="lastName" id="lastName" />
                                            <p className="danger"><ErrorMessage name="lastName" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="birthday">Birthday</Label><br />
                                            <Field name="birthday" id="birthday" type="date" />
                                            <p className="danger"><ErrorMessage name="birthday" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="roleId">Role</Label><br />
                                            <select name="roleId" onChange={ this.onChangeRole} className="form-control">
                                                <option>USER</option>
                                                <option>ADMIN</option>
                                            </select>

                                        </FormGroup>
                                        <Button color="primary">Submit</Button>
                                        <Button type="button" onClick={this.goBack.bind(this)}>Cancel</Button>
                                    </div>
                                </div>
                            </div>
                        </Form>
                    </Formik>
                </div>
            );
        } else {
            return (
                <div>
                    <h2>Error occured</h2>
                    <h3>{this.state.errorMsg}</h3>
                    <Button type="button" onClick={this.goBack.bind(this)}>Try to go back</Button>
                </div>
            )
        }
    }
}

const CreateSchema = Yup.object().shape({
    firstName: Yup.string()
        .min(2, 'Too Short!')
        .max(24, 'Too Long!')
        .required('Required'),
    lastName: Yup.string()
        .min(2, 'Too Short!')
        .max(24, 'Too Long!')
        .required('Required'),
    email: Yup.string()
        .email('Invalid email')
        .required('Required'),
    login: Yup.string()
        .min(4, 'Too short!')
        .required('Required')
        .max(24, 'Too Long!'),
    password: Yup.string()
        .min(4, 'Too short')
        .required('Required')
        .max(16, 'Too Long!'),

});


export default CreateUser;