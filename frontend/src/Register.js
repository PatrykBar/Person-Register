import React, { Component } from 'react';
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'

import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie'
import { withRouter } from 'react-router-dom'

import './Login.css';

import RegistrationAlert from './RegistrationAlert.js';

class Register extends Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        const { cookies } = props;
        this.state = {
              csrfToken: cookies.get('XSRF-TOKEN')
        };
        this.registrationAlert = React.createRef();
    }

    handleSubmit = event => {
        event.preventDefault();
        this.registerUser(event.target.username.value, event.target.password.value);
    }

    registerUser(username, password) {
        const {csrfToken} = this.state;

        fetch('http://localhost:8080/register', {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': csrfToken,
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        username: username,
                        password: password,
                    }),
                }).then(function(response) {
                  if (response.status === 200) {
                    this.showRegistrationAlert("success", "User registered!", "You can now log in using your credentials.");
                  } else if (response.status === 422) {
                    this.showRegistrationAlert("danger", "User already exists", "Please choose a different name.");
                  } else {
                    this.showRegistrationAlert("danger", "User not registered!", "Something went wrong.");
                  }
                }.bind(this)).catch(function(error) {
                  this.showRegistrationAlert("danger", "Error", "Something went wrong.");
                }.bind(this));
      }

    showRegistrationAlert(variant, heading, message) {
        this.registrationAlert.current.setVariant(variant);
        this.registrationAlert.current.setHeading(heading);
        this.registrationAlert.current.setMessage(message);
        this.registrationAlert.current.setVisible(true);
    }

    render() {
        return (
        <>
            <div className = "Login" >
            <h1 className="RegisterHeader">Register</h1>

            <Form onSubmit = { this.handleSubmit } >

            <Form.Group controlId = "username" size = "lg">
            <Form.Label> Username </Form.Label>
            <Form.Control autoFocus name = "username"/>
            </Form.Group>

            <Form.Group controlId = "password" size = "lg" >
            <Form.Label > Password </Form.Label>
            <Form.Control type = "password" name = "password"/>
            </Form.Group>

            <Button block size = "lg" type = "submit">
            Register
            </Button>

            </Form>

            </div>

            <RegistrationAlert ref = { this.registrationAlert }/>

        </>
        );
    }

}

export default withCookies(withRouter(Register));