import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { Button, FormGroup, Label } from 'reactstrap';
class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userRole: '',
            errorMsg: '',
        }
    }

    render() {
        const { state = {} } = this.props.location;
        const { error } = state;
        return (
            <div>
                <Formik
                    initialValues={{
                        username: '',
                        password: ''
                    }}
                    validationSchema={CreateSchema}
                    onSubmit={values => {
                        console.log(values);
                        fetch('http://10.10.35.192:8080/Web_Services/token/generate-token', {
                            method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',

                            },
                            body: JSON.stringify(values)
                        })
                            .then(response => {
                                return response.json()
                            })
                            .then(data => {
                                if (data) {
                                    localStorage.setItem('user', JSON.stringify(data))
                                    if (data.result.roleId.id === 1) {
                                        this.props.history.push('/user')
                                    } else {
                                        this.props.history.push('/admin')
                                    }
                                }
                            })
                            .catch(error => this.setState({ errorMsg: "Invalid password or login" }));
                    }}
                >
                    <Form>
                        <div className="container">
                            <div className="center">
                                <div className="row align-items-center justify-content-center">
                                    <div className="login-container">                                        <p className="danger">{error}</p>
                                        <FormGroup>
                                            <Label className="control-label col-sm-2" for="username">Login</Label><br />
                                            <Field className="col-sm-10"  name="username" id="username" />
                                            <p className="danger"><ErrorMessage name="username" /></p>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label className="control-label col-sm-2" for="password">Password</Label><br />
                                            <Field type="password"  className="col-sm-10" name="password" id="password" />
                                            <p className="danger"><ErrorMessage name="password" /></p>
                                        </FormGroup>
                                        <p className="danger">{this.state.errorMsg}</p>
                                        <Button color="primary" >Submit</Button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </Form>
                </Formik>
            </div>
        );
    }

}

const CreateSchema = Yup.object().shape({
    username: Yup.string()
        .min(4, 'At least 4')
        .required('Required')
        .max(24, 'Too Long!'),
    password: Yup.string()
        .min(4, 'At least 4')
        .required('Required')
        .max(16, 'Too Long!'),
});

export default Login;