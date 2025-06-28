import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import LinkedAccountService from "../services/LinkedAccountService";
import AuthenticationService from "../services/AuthenticationService";
import { createAuthSuccess } from '../actions/AuthenticationActions';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function OAuthLoginPage(){
    const accessToken = useSelector((state) => state.auth.token);

    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const linkOrLogin = async (account) => {
        if (accessToken){

            const data = {  
                id: account.id,
                email: account.email,
                name: account.name,
                provider: account.provider
            }

            try{
                const response = await LinkedAccountService.create(data)

                toast.success("Your account is successfully linked", {position: toast.POSITION.BOTTOM_CENTER, toastId:"linking_success", draggable: false });
                navigate('/account');
            } catch (error){
                const errors = error.response.data?.errors;

                if (errors) {
                    errors.forEach((error, index) => {
                        if (error.error === "EXISTING_LINKED_ACCOUNT"){
                            toast.error("This account is already linked", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString() });
                        }
                    else{
                        toast.error(`Unexpected error ocurred`, { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                    }
                    });
                    navigate("/account")
                }
            }

        } else{
            try{
                const response = await AuthenticationService.oauthLogin(account.id)


                const receivedToken = response.data.accessToken;
                    
                if(receivedToken){
                    dispatch(createAuthSuccess({ token: receivedToken }));

                    navigate('/');
                }
            } catch (error){
                const errors = error.response.data?.errors;

                if (errors) {
                    errors.forEach((error, index) => {
                        if (error.error === "INVALID_CREDENTIALS"){
                            toast.error("The provided social credentials are not associated with an existing account", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString() });
                        }
                    else{
                        toast.error(`Unexpected error ocurred`, { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                    }
                    });
                    navigate("/login")
                }
            }
        }
    }

    useEffect(() => {

        const searchParams = new URLSearchParams(location.search);
        const id = searchParams.get('id');
        const name = searchParams.get('name');
        const email = searchParams.get('email');
        const provider = searchParams.get('provider');
        const action = searchParams.get('action');

        if (!id || !name || !email || !action){
            navigate("/")
        }

        const account = {
            id: id,
            name: name,
            email: email,
            provider: provider
        }

        linkOrLogin(account);
        
      }, []);

    return(
        <div class="spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
    );

}


export default OAuthLoginPage