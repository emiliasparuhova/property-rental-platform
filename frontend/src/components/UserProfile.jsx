import {React, useState, useEffect} from 'react';
import { useParams } from 'react-router-dom';
import style from './UserProfile.module.css';
import UserService from "../services/UserService";
import "bootstrap/dist/css/bootstrap.min.css";

function UserProfile(){
    const [user, setUser] = useState({
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
    const [userProfilePicture, setUserProfilePicture] = useState('')
    const { id } = useParams();

    useEffect(() => {
        const getUserData = async () => {
            try {
                const response = await UserService.get(id);
                setUser(response.data);
                setUserProfilePicture(`data:image/jpeg;base64,${response.data.profilePicture}`);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        getUserData();
    }, [id]);
   

    return (
        <div className={`container ${style.profileContainer}`}>
            <div className='row'>
                <div className='me-5 col-sm-12 col-md-12 col-lg-6'>
                    <div className="text-center">
                        {user.profilePicture ? (
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
                    </div>
        

                    <h2 className="text-center mb-4">{user.name}</h2>
                </div>
                <div className='col-sm-12 col-md-12 col-lg-4'>
                    <div className={style.profileInfo}>
                    {user.description ? (
                        <p className='mb-5'><em><b>Description:</b> {user.description}</em></p>
                    ) : (
                        <p className='mb-5'><em><b>Description:</b> This user has no description</em></p>
                    )}
                        <dl className={`row d-flex flex-column ${style.horizontalList}`}>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Address</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.address.city} {user.address.street} {user.address.zipcode}</dd>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Gender</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.gender}</dd>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Birth Date</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.birthDate}</dd>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Join Date</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.joinDate}</dd>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Role</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.role}</dd>
                                    </div>
                                </div>
                            </div>
                            <div className="col">
                                <div className="row">
                                    <div className="col">
                                        <dt>Status</dt>
                                    </div>
                                    <div className="col">
                                        <dd>{user.status}</dd>
                                    </div>
                                </div>
                            </div>
                        </dl>
                    </div>
                </div>
            </div>
      </div>
    );
}

export default UserProfile;