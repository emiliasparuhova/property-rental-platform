import { useEffect, useState } from "react";
import UserService from "../services/UserService.js";
import style from './UpdateUserProfile.module.css'
import AuthenticationService from "../services/AuthenticationService.js";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import OAuth2Service from "../services/OAuth2Service.js";
import LinkedAccountService from "../services/LinkedAccountService.js";
import LinkedAccount from "./LinkedAccount.jsx";

function UpdateSecurityData(props){

    const [user, setUser] = useState({
        id: "",
        name: "",
        email: "",
        description: "",    
        address : 
        {
            city: "",
            street: "",
            zipcode: ""
        },
        gender: "",
        birthDate: "",
        profilePicture: "",
        joinDate: "",
        role: "",
        status: "",
    });

    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [linkedAccounts, setLinkedAccounts] = useState([])

    useEffect( () => {
        if (props.user){
            setUser(props.user)
            fetchLinkedAccounts(props.user.id)
        }

    }, [props.user]);

    const fetchLinkedAccounts = async (id) => {
      try{
        const response = await LinkedAccountService.get(id)

        setLinkedAccounts(response.data)
      } catch (error){
        console.error(error)
      }
    }

    const handleInputChange = (e, field) => {
        const value = e.target.value;
        setUser((prevUser) => ({ ...prevUser, [field]: value }));
    };

    const handleCurrentPasswordChange = (e) => {
        setCurrentPassword(e.target.value);
    };

    const handleNewPasswordChange = (e) => {
        setNewPassword(e.target.value);
    };

    const updateUserAccount = async (e) => {
        e.preventDefault();
        try{
            const updatedData =
            {
                name: user.name,
                email: user.email,
                description: user.description,
                address: 
                {
                    city: user.address.city,
                    street: user.address.street,
                    zipcode: user.address.zipcode
                },
                gender: user.gender,
                birthDate: user.birthDate,
                profilePicture: user.profilePicture,
                status: user.status,
            }
            const response = await UserService.update(user.id, updatedData);

            toast.success("Email successfully updated", { position: toast.POSITION.BOTTOM_CENTER, toastId: "updated_email_success" });


        } catch (error) {
            if (error.response && error.response.data && error.response.data.errors) {
                const errors = error.response.data.errors;
    
                errors.forEach((error, index) => {
                    if (error.error === "must not be blank" || error.error === "must not be null") {
                        toast.error(`The ${error.field} field is mandatory`, { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                    }
                    if (error.error === "must be a well-formed email address") {
                        toast.error("The email you provided is invalid", { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                    }
                    if (error.error === "EMAIL_IN_USE") {
                        toast.error("This email is already in use", { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                    }
                });
            }
        }
    }

    const updateUserPassword = async (e) => {
        e.preventDefault();
    
        try {
            const data = {
                email: user.email,
                plainTextPassword: currentPassword
            };
    
            const response = await AuthenticationService.create(data);
    
            if (response.data && response.data.accessToken) {    
                const updatedData = {
                    name: user.name,
                    email: user.email,
                    description: user.description,
                    address: {
                        city: user.address.city,
                        street: user.address.street,
                        zipcode: user.address.zipcode
                    },
                    gender: user.gender,
                    birthDate: user.birthDate,
                    profilePicture: user.profilePicture,
                    status: user.status,
                    plainTextPassword: newPassword
                };
    
                const userResponse = await UserService.update(user.id, updatedData)
                
                toast.success("Password successfully updated", { position: toast.POSITION.BOTTOM_CENTER, toastId: "updated_password_success" });
            }
        } catch (error) {
            if (error.response && error.response.data && error.response.data.errors) {
                const errors = error.response.data.errors;
                
                errors.forEach((error, index) => {
                    if (error.error === "INVALID_CREDENTIALS") {
                        toast.error("Current password doesn't match", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString() });
                    }
                });
            }
        }
    };

    const handleRemoveLinkedAccount = (removedAccountId) => {
      setLinkedAccounts((prevAccounts) =>
        prevAccounts.filter((account) => account.id !== removedAccountId)
      );
    };

    const redirectToGoogle = async () => {
      try {
        const response = await OAuth2Service.getGoogleRedirectUri();

        window.location.href = response.data;
      } catch (error) { 
        console.error('Error initiating OAuth:', error);
      }
    };
    

    return (
        <div>
          <div className={`container d-flex flex-column ${style.profileContainer}`}>
            <form onSubmit={updateUserPassword} className="col-lg-6 col-md-12 mb-3">
              <div className="row text-center d-flex flex-column">
                <h3>Change Password</h3>
                <div className="mb-3">
                  <input
                    type="password"
                    name="currentPassword"
                    value={currentPassword}
                    onChange={handleCurrentPasswordChange}
                    className="form-control"
                    placeholder="Current Password"
                  />
                </div>
                <div className="mb-3">
                  <input
                    type="password"
                    name="newPassword"
                    value={newPassword}
                    onChange={handleNewPasswordChange}
                    className="form-control"
                    placeholder="New Password"
                  />
                </div>
                <div className="mb-3">
                  <button type="submit" className="btn btn-primary">
                    Update Password
                  </button>
                </div>
              </div>
            </form>
        
            <form onSubmit={updateUserAccount} className="col-lg-6 col-md-12 mb-3">
              <div className="row text-center d-flex flex-column">
                <h3>Change Email</h3>
                <div className="mb-3">
                  <input
                    type="text"
                    name="email"
                    value={user.email || ''}
                    onChange={(e) => handleInputChange(e, 'email')}
                    className="form-control"
                    placeholder="New Email"
                  />
                </div>
                <div className="mb-3">
                  <button type="submit" className="btn btn-primary">
                    Update Email
                  </button>
                </div>
              </div>
            </form>
          </div>
          <div className="text-center">
            <h3 className="mt-5">Linked Accounts</h3>
          </div>
          <div className={`container mt-3 d-flex flex-row text-center justify-content-center`}>
              {linkedAccounts && linkedAccounts.length > 0 ? (
                linkedAccounts.map((linkedAccount, index) => (
                  <LinkedAccount 
                    linkedAccount={linkedAccount} 
                    key={index}
                    onRemoveLinkedAccount={handleRemoveLinkedAccount}
                  />
                ))
              ) : (
                <div className="d-flex flex-row align-items-center">
                  <p className="fst-italic me-5">You haven't linked your Google account</p>  
                  <div className="btn fw-bold border rounded" onClick={redirectToGoogle}>
                    <img
                          src="/images/google_icon.png"
                          alt="google icon"
                          className={`${style.providerIcon}`}
                    />
                    Sign in with Google
                  </div>
                </div>
              )}
            </div>
        </div>
      );
      
}


export default UpdateSecurityData;