import { React, useState, useEffect, useReducer } from "react";
import styles from './NavBar.module.css';
import { NavLink } from "react-router-dom";
import { useSelector } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.min.css';
import { logout } from '../actions/AuthenticationActions';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import UserService from "../services/UserService.js";
import ChatService from "../services/ChatService.js";
import { Client } from '@stomp/stompjs';
import webSocketConfig from '../webSocketConfig'


function NavBar() {
    const accessToken = useSelector((state) => state.auth.token);
    const [userId, setUserId] = useState(null)
    const [userRole , setUserRole] = useState('')
    const [userProfilePicture, setUserProfilePicture] = useState('')
    const [stompClient, setStompClient] = useState();
    const [chats, setChats] = useState([])
    const [newMessages, setNewMessages] = useState(false)

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logoLink = {
        id: 1,
        path: "/",
        imageSrc: "/images/logo.png",
        altText: "logo"
    }

    const autchenticatedLinks = [
        {
            id: 2,
            path: "/account",
            text: "Account Settings"
        },
        {
            id: 3,
            path: `/users/${userId}`,
            text: "Profile"
        }
    ]

    const landlordLinks = [
        {
            id: 4,
            path: "/my-adverts",
            text: "Manage Adverts"
        }
    ]

    const tenantLinks = [
        {
            id: 5,
            path: "/favourite-adverts",
            text: "Favourites"
        }
    ]

    const logoutUser = (e) => {
        e.preventDefault();

        console.log("You have successfully logged out");
        dispatch(logout());
        navigate('/');
    }

    useEffect(() => {
        if (accessToken) {
          const fetchData = async () => {
            try {
              const userId = await fetchUser();
              const chatsResponse = await fetchChats(userId);
              setupStompClient(chatsResponse, userId);
            } catch (error) {
              console.error(error);
            }
          };
          
          fetchData();
        }
      }, [accessToken, userProfilePicture]);
      

    const fetchUser = async () => {
        try {
            if (accessToken) {
                const response = await UserService.getUserByToken();
                setUserId(response.data.id);
                if (response.data.profilePicture){
                    setUserProfilePicture(`data:image/jpeg;base64,${response.data.profilePicture}`);
                }
                else {
                    setUserProfilePicture('')
                }
                if (response.data.role) {
                    setUserRole(response.data.role)
                }
                else {
                    setUserRole('')
                }

                return response.data.id
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    const fetchChats = async (userId) => {
        try{
            const response = await ChatService.getChatsByUser(userId)
            setChats(response.data)

            return response
        } catch (error){
            console.error(error)
        }
    }

    const setupStompClient = async (chatsResponse, userId) => {
      try {
        const stompClient = new Client({
          brokerURL: webSocketConfig.url,
          reconnectDelay: 5000,
          heartbeatIncoming: 4000,
          heartbeatOutgoing: 4000,
        });
    
        const chats = chatsResponse.data

        stompClient.onConnect = () => {
          chats.map(chat => {
            stompClient.subscribe(`/user/${chat.id}/queue/inboxmessages`, (data) => {
              onMessageReceived(data, userId);
            });
          });
          };
    
        stompClient.activate();
        setStompClient(stompClient);
      } catch (error) {
        console.error(error);
      }
    };

    const onMessageReceived = (data, userId) => {
        const message = JSON.parse(data.body);

        if (message.sender.id !== userId){
            setNewMessages(true);
        }
    }

    return (
      <nav className={`${styles.navBar}`}>
            <div className={styles.logo}>
              {logoLink.imageSrc ? (
                  <NavLink to={logoLink.path}>  
                      <img src={logoLink.imageSrc} alt={logoLink.altText} className={styles.logoImage}/>
                  </NavLink> 
              ) : (
                  <NavLink to={logoLink.path}>{logoLink.text}</NavLink>
              )}
            </div>
            {accessToken && (
            <div onClick={() => setNewMessages(false)}>
                <NavLink to='my-chats'>
                    {newMessages ? (
                        <img className={styles.messageIcon} alt="messages" src="/images/new_messages_icon.png"/>
                    ) : (
                        <img className={styles.messageIcon} alt="messages" src="/images/messages_icon.png"/>
                    )}
                </NavLink>  
            </div>
            )}
        <div className={styles.dropdown}>
            {accessToken ? (
                <>
                    <button>
                        {userProfilePicture ? (
                            <img
                                src={userProfilePicture}
                                alt="Profile Picture"
                                className={`img-fluid rounded-circle ${styles.profilePicture}`}
                            />
                        ) : (
                            <img
                                src="/images/default_pfp.png"
                                alt="Default Profile Picture"
                                className={`img-fluid rounded-circle ${styles.profilePicture}`}
                            />
                        )}
                    </button>
                    <div className={styles.dropdownContent}>
                        <ul className={styles.navLinks}>
                        {autchenticatedLinks.map((link) => (
                            <li key={link.id}>
                                {link.imageSrc ? (
                                    <NavLink to={link.path}>
                                        <img src={link.imageSrc} alt={link.altText} />
                                    </NavLink>
                                ) : (
                                    <NavLink to={link.path}>{link.text}</NavLink>
                                )}
                            </li>
                        ))}
                        {userRole === 'LANDLORD' && (
                            <ul>
                                {landlordLinks.map((link) => (
                                    <li key={link.id}>
                                        {link.imageSrc ? (
                                            <NavLink to={link.path}>
                                                <img src={link.imageSrc} alt={link.altText} />
                                            </NavLink>
                                        ) : (
                                            <NavLink to={link.path}>{link.text}</NavLink>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        )}
                        {userRole === 'TENANT' && (
                            <ul>
                                {tenantLinks.map((link) => (
                                    <li key={link.id}>
                                        {link.imageSrc ? (
                                            <NavLink to={link.path}>
                                                <img src={link.imageSrc} alt={link.altText} />
                                            </NavLink>
                                        ) : (
                                            <NavLink to={link.path}>{link.text}</NavLink>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        )}
                            <li>
                                <NavLink to="/" key="logout" onClick={logoutUser}>Logout</NavLink>
                            </li>
                        </ul>
                    </div>
                </>
            ) : (
                <>
                    <NavLink className="btn" to="/login">Login</NavLink>
                </>
            )}
        </div>
      </nav>
    );
}

export default NavBar;
