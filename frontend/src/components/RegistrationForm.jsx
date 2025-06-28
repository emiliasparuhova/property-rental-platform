import style from "./RegistrationForm.module.css";
import React, {useEffect, useState} from 'react'; 
import { useNavigate } from 'react-router-dom';
import UserService from "../services/UserService";
import "bootstrap/dist/css/bootstrap.min.css";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function RegistrationForm(){

    const initialUserState = {
        name: "",
        email: "",
        password: "",
        role: null
      };

    const [user, setUser] = useState(initialUserState);
    const navigate = useNavigate();

    const handleInputChange = e => {
        const { name, value } = e.target;
        setUser({ ...user, [name]: value });
    }
  
    const saveUser = (e) => {
        e.preventDefault(); 
    
        const data = {
            name: user.name,
            email: user.email,
            password: user.password,
            role: user.role
        };
    
        const createUserPromise = new Promise((resolve, reject) => {
            UserService.create(data)
                .then(response => {
                    resolve(response.data);
                })
                .catch(response => {
                    reject(response);
                });
        });
    
        createUserPromise
            .then(user => {
            console.log('User created successfully. User ID:', user.id);

            setUser(initialUserState);

            toast.success("Your registration was successful", {position: toast.POSITION.BOTTOM_CENTER, toastId:"registreation_success", draggable: false });
            navigate('/login');
            })
            .catch(response => {
            console.error('Error creating user:', response);

            const errors = response.response.data.errors;

            if (errors) {
                errors.forEach((error, index) => {
                    if (error.error === "must not be blank" || error.error === "must not be null") {
                        toast.error(`The ${error.field} field is mandatory`, { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
                    }
                    if (error.error === "must be a well-formed email address"){
                        toast.error("The email you provided is invalid", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
                    }
                    if (error.error === "EMAIL_IN_USE"){
                        toast.error("This email is already in use", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
                    }
                }
            )}

            });      
    };
    

    return(
        <div className='row'>
            <div className={`col  ${style.containerRegister}`}>
                <h2>Register</h2>
                <form method="post" onSubmit={saveUser}>
                    <div className={style.radioContainer}>
                        <input
                            type='radio'
                            id='tenant'
                            name='role'
                            onChange={handleInputChange}
                            value="TENANT"
                            checked={user.role === "TENANT"}
                        />
                        <label htmlFor='tenant'>TENANT</label>

                        <input
                            type='radio'
                            id='landlord'
                            name='role'
                            onChange={handleInputChange}
                            value="LANDLORD"
                            checked={user.role === "LANDLORD"}
                        />
                        <label htmlFor='landlord'>LANDLORD</label>
                    </div>
                    <div>
                        <input htmlFor="name" name="name" type="text" placeholder="Full Name" 
                        onChange={handleInputChange}
                        value={user.name || ''}
                        />
                    </div>
                    <div>
                        <input htmlFor="email" name="email" type="text" placeholder="Email" 
                        onChange={handleInputChange}
                        value={user.email || ''}
                        />
                    </div>

                    <div>
                        <input htmlFor="password" name="password" type="password" placeholder="Password" 
                        onChange={handleInputChange}
                        value={user.password || ''}
                        />
                    </div>
                    <div>
                        <input type="submit" value="Create Your Account"/>
                    </div>
                </form>
            </div>
        </div>
    );
}


export default RegistrationForm