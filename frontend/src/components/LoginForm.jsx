import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import style from "./LoginForm.module.css";
import AuthenticationService from "../services/AuthenticationService";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { connect } from 'react-redux';
import { createAuthSuccess } from '../actions/AuthenticationActions';
import OAuth2Service from "../services/OAuth2Service";

function LoginForm(){
    const registrationPagePath = "/registration";
    const navigate = useNavigate();
    const dispatch = useDispatch();
    
    const accessToken = useSelector((state) => state.auth.token);

    useEffect(() => {
        if (accessToken){
            navigate('/')
        }
    }, [])

    const initialUserCredentials = {
        email: "",
        plainTextPassword: ""
    }

    const [userCredentials, setUserCredentials] = useState(initialUserCredentials);

    const handleInputChange = e => {
        const { name, value } = e.target;
        setUserCredentials({ ...userCredentials, [name]: value });
    }

    const authenticateUser = (e) => {
        e.preventDefault();

        const data = {
            email: userCredentials.email,
            plainTextPassword: userCredentials.plainTextPassword
        }

        const authenticateUserPromise = new Promise((resolve, reject) => {
            AuthenticationService.create(data)
                .then(response => {
                    resolve(response.data)
                })
                .catch(error => {
                    reject(error);
                });
        });

        authenticateUserPromise
            .then(response => {
                const receivedToken = response.accessToken;
                
                if(receivedToken){
                    console.log("You have successfully logged in");
                    dispatch(createAuthSuccess({ token: receivedToken }));
                    navigate('/');
                }
                else {
                console.log("Credentials don't match");
            }
            })
            .catch(response => {
            const errors = response.response.data?.errors;

            if (errors) {
                errors.forEach((error, index) => {
                    if (error.error === "INVALID_CREDENTIALS"){
                        toast.error("Invalid login credentials", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString() });
                    }
                else{
                    toast.error(`Unexpected error ocurred`, { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                }
                });
            }
        });      
    }

    const redirectToGoogle = async () => {
        try {
          const response = await OAuth2Service.getGoogleRedirectUri();
  
          window.location.href = response.data;
        } catch (error) { 
          console.error('Error initiating OAuth:', error);
        }
      };


    return(
        <>
            <div className="row">
                <div className={`col ${style.containerLogin}`}>
                    <h2>Login</h2>
                    <form method="post" onSubmit={authenticateUser}>
                        <div>
                            <input htmlFor="email" name="email" type="text" placeholder="Email" 
                            onChange={handleInputChange}
                            value={userCredentials.email || ''}
                            />
                        </div>

                        <div>
                            <input htmlFor="PlainTextPassword" name="plainTextPassword" type="password" placeholder="Password" 
                            onChange={handleInputChange}
                            value={userCredentials.plainTextPassword || ''}
                            />
                        </div>
                        <div>
                            <input type="submit" value="Login" />
                        </div>
                    </form>

                    <div>
                        <p>
                            Don't have an account?{" "}
                            <NavLink to={registrationPagePath}>Sign up</NavLink>
                        </p>
                    </div>
                </div>
            </div>
            <hr className="mt-5 mb-4"></hr>
            <div className="row d-flex flex-column text-center justify-content-center align-items-center">
                <p className="small mb-4">or login with your linked account</p>
                <div className="btn fw-bold border rounded w-50" onClick={redirectToGoogle}>
                <img
                    src="/images/google_icon.png"
                    alt="google icon"
                    className={`${style.providerIcon}`}
                />
                Sign in with Google
                </div>
            </div>      
        </>
    );
}

const mapDispatchToProps = (dispatch) => ({
    createAuthSuccess: (authData) => dispatch(createAuthSuccess(authData)),
  });
  
  export default connect(null, mapDispatchToProps)(LoginForm)
