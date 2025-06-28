import { useEffect, useState } from "react";
import UserService from "../services/UserService.js";
import { useSelector } from "react-redux";
import style from './UpdateUserProfile.module.css'
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function UpdateUserProfile(props){
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

    const [selectedFile, setSelectedFile] = useState(null);
    const [userProfilePicture, setUserProfilePicture] = useState('')

    useEffect( () => {
        if (props.user){
            setUser(props.user)
            setUserProfilePicture(`data:image/jpeg;base64,${props.user.profilePicture}`)
        }

    }, [props.user]);

    const handleInputChange = (e, field) => {
        setUser((prevUser) => {
            if (field.startsWith('address.')) {
                const addressField = field.split('.')[1];
                return {
                    ...prevUser,
                    address: {
                        ...prevUser.address,
                        [addressField]: e.target.value,
                    },
                };
            } else {
                return {
                    ...prevUser,
                    [field]: e.target.value,
                };
            }
        });
    };
    

    const handleFileChange = (e) => {
        const file = e.target.files[0];
      
        if (file) {
          const reader = new FileReader();
      
          reader.onloadend = () => {
            const base64String = reader.result.split(",")[1];
            setSelectedFile(base64String);
          };
      
          reader.readAsDataURL(file);
        }
    };   
      
      
    const clearImage = () => {
        setSelectedFile(null);

        const fileInput = document.querySelector('input[name="profilePicture"]');
        if (fileInput) {
            fileInput.value = '';
        }
    };

    const createDataUrl = (base64String) => {
        const byteCharacters = atob(base64String);
        const byteNumbers = new Array(byteCharacters.length);
      
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
      
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'image/*' });
        return URL.createObjectURL(blob);
    };
      
    const updateUserAccount = async (e) => {
        e.preventDefault();
        try{
            const updatedProfilePicture = selectedFile || user.profilePicture

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
                profilePicture: updatedProfilePicture,
                status: user.status,
            }
            const response = await UserService.update(user.id, updatedData);

            toast.success("Information successfully updated", { position: toast.POSITION.BOTTOM_CENTER, toastId: "updated_success" });

        } catch (error) {
            if (error.response && error.response.data && error.response.data.errors) {
                const errors = error.response.data.errors;
    
                errors.forEach((error) => {
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


    return (
        <div className={`container ${style.profileContainer}`}>       
            <form onSubmit={updateUserAccount}>
                <div className="row justify-content-center">
                    <div className="col-lg-6 col-md-12 mb-3">          
                        <div className="mb-3 text-center">
                            {selectedFile ? (
                                <div className="image-preview">
                                    <img
                                        src={createDataUrl(selectedFile)}
                                        alt="Profile Preview"
                                        className={`img-fluid rounded-circle ${style.profilePicture}`}
                                    />
                                    <button type="button" onClick={clearImage} className="btn mt-3 mb-3">
                                        Clear Image
                                    </button>
                                </div>
                            ) : user.profilePicture ? (
                                <img
                                    src={userProfilePicture}
                                    alt="Profile Picture"
                                    className={`img-fluid rounded-circle ${style.profilePicture}`}
                                />
                            ) : (
                                <img
                                    src="/images/default_pfp.png"
                                    alt="Default Profile Picture"
                                    className={`img-fluid rounded-circle ${style.profilePicture}`}
                                />
                            )}
                            <input
                                type="file"
                                name="profilePicture"
                                accept="image/*"
                                onChange={handleFileChange}
                                className="form-control"
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                name="name"
                                value={user.name || ''}
                                onChange={(e) => handleInputChange(e, 'name')}
                                className="form-control"
                                placeholder="Name"
                            />
                        </div>
                        <div className="mb-3">
                        <textarea
                                name="description"
                                value={user.description || ''}
                                onChange={(e) => handleInputChange(e, 'description')}
                                className="form-control"
                                rows="4" 
                                placeholder="Describe yourself..."
                            />
                        </div>
                    </div>

                    <div className="col-lg-6 col-md-12 mb-3 mt-5">
                        <div className="mb-3">
                            <input
                                type="text"
                                name="city"
                                value={user.address.city || ''}
                                onChange={(e) => handleInputChange(e, 'address.city')}
                                className="form-control"
                                placeholder="City"
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                name="street"
                                value={user.address.street || ''}
                                onChange={(e) => handleInputChange(e, 'address.street')}
                                className="form-control"
                                placeholder="Street"
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                type="text"
                                name="zipcode"
                                value={user.address.zipcode || ''}
                                onChange={(e) => handleInputChange(e, 'address.zipcode')}
                                className="form-control"
                                placeholder="Zipcode"
                            />
                        </div>
                        <div className="mb-3">
                            <select
                                name="gender"
                                value={user.gender || ''}
                                onChange={(e) => handleInputChange(e, 'gender')}
                                className="form-select"
                            >
                                <option value="">Select Gender</option>
                                <option value="FEMALE">Female</option>
                                <option value="MALE">Male</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>
                        <div className="mb-3">
                            <label>Birth Date</label>
                            <input
                                type="date"
                                name="birthDate"
                                value={user.birthDate || ''}
                                onChange={(e) => handleInputChange(e, 'birthDate')}
                                className="form-control"
                            />
                        </div>
                        <div className="mb-3">
                            <select
                                name="status"
                                value={user.status || ''}
                                onChange={(e) => handleInputChange(e, 'status')}
                                className="form-select"
                            >
                                <option value="">Select Status</option>
                                <option value="WORKING">Working</option>
                                <option value="STUDENT">Student</option>
                                <option value="WORKING_STUDENT">Working Student</option>
                                <option value="UNEMPLOYED">Unemployed</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" className="btn btn-primary">
                            Save Changes
                    </button>            
                </div>
            </form>
        </div>
    );
}


export default UpdateUserProfile;