import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import UpdatePersonalData from '../components/UpdatePersonalData';
import UserService from '../services/UserService';
import UpdateSecurityData from '../components/UpdateSecurityData';
import AuthenticationService from '../services/AuthenticationService';
import style from './AccountPage.module.css'


function AccountPage(){

    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate();

    const [activeTab, setActiveTab] = useState("updatePersonalData")
    const [currentPassword, setCurrentPassword] = useState('');

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

    const switchTab = (tab) => {
        setActiveTab(tab);
    };

    const handleCurrentPasswordChange = (e) => {
        setCurrentPassword(e.target.value);
    };

    const getUserByToken = async () => {
        try {
            const response = await UserService.getUserByToken();
            setUser(response.data);
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    }

    const deleteUserAccount = async () => {
        try {
            const data = {
                email: user.email,
                plainTextPassword: currentPassword
            };
    
            const response = await AuthenticationService.create(data);
    
            if (response.data && response.data.accessToken){
                await UserService.remove(user.id)
                navigate("/login");
            }
        }catch (error) {
            if (error.response && error.response.data && error.response.data.errors) {
                const errors = error.response.data.errors;
                
                errors.forEach((error, index) => {
                    if (error.error === "INVALID_CREDENTIALS") {
                        toast.error("Current password doesn't match", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString() });
                    }
                });
            }
        }
    }

    useEffect(() =>{
        if (!accessToken){
            navigate("/login");
        }

        getUserByToken()
            .then(user => {
                console.log('User Data:', user);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }, [accessToken])

    return (
        <div className={`container mt-5 ${style.accountContainer}`}>
            <div className="row justify-content-center mt-5">
                <div className="col-sm-12 col-md-12 col-lg-10 col-xl-8">
                    <div className={`d-flex flex-row ${style.buttonContainer}`}>
                        <button
                        type="button"
                        className={`w-100 me-3 ${style.tabButton} ${activeTab === "updatePersonalData" ? style.active : ''}`}
                        onClick={() => switchTab("updatePersonalData")}
                        >
                        Personal Information
                        </button>
                        <button
                        type="button"
                        className={`w-100 ms-3 ${style.tabButton} ${activeTab === "updateSecuritySettings" ? style.active : ''}`}
                        onClick={() => switchTab("updateSecuritySettings")}
                        >
                        Security Settings
                        </button>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="">
                {activeTab === "updatePersonalData" ? (
                    <UpdatePersonalData user={user} />
                ) : activeTab === "updateSecuritySettings" ? (
                    <>
                    <UpdateSecurityData user={user} />
                    <div className="row d-flex mt-5 justify-content-center">
                        <div className="mb-3 d-flex flex-column align-items-center">
                            <h2>Want to delete your account?</h2>
                            <p>Input your password to confirm this action.</p>
                            <input
                                type="password"
                                name="currentPassword"
                                value={currentPassword}
                                onChange={handleCurrentPasswordChange}
                                className="form-control"
                                placeholder="Current Password"
                            />
                        </div>
                        <div className="mb-3 d-flex flex-column align-items-center">
                            <button type="button" className="" onClick={deleteUserAccount}>
                                Delete Account
                            </button>
                        </div>
                    </div>
                    </>
                ) : null}
                </div>
            </div>
        </div>
    );
}

export default AccountPage