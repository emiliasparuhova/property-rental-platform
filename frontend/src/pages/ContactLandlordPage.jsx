import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import UserService from "../services/UserService";
import ChatService from "../services/ChatService";
import MessageService from "../services/MessageService";
import style from "./ContactLandlordPage.module.css"
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function ContactLandlordPage(){
    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate();
    const location = useLocation();

    const initialChatState = {
        advertId: 0,
        landlordId: 0,
        tenantId: 0
    }

    const initialMessageState = {
        senderId: 0,
        content: "",
        chatId: 0
    }

    const [chat, setChat] = useState(initialChatState)
    const [message, setMessage] = useState(initialMessageState)

    useEffect(() => {
        if (!accessToken) {
          navigate("/login");
        }

        setChat((prevChat) => ({ ...prevChat, advertId: location.state?.advert.id }));
        setChat((prevChat) => ({ ...prevChat, landlordId: location.state?.advert.creator.id }));

        fetchUser()
    }, []);

    const fetchUser = async () => {
        try {
            if (accessToken) {
            const response = await UserService.getUserByToken();

            if(response.data.role !== "TENANT"){
                navigate("/")
            }

            setChat((prevChat) => ({ ...prevChat, tenantId: response.data.id }));
            setMessage((prevMessage) => ({ ...prevMessage, senderId: response.data.id}));
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    const handleInputChange = e => {
    const { name, value } = e.target;
    setMessage(prevState => ({
        ...prevState,
        [name]: value
    }));
    };

    const sendMessage = async (e) => {
        e.preventDefault();
    
        const chatData = {
            advert: {
                id: chat.advertId
            },
            landlord: {
                id: chat.landlordId
            },
            tenant: {
                id: chat.tenantId
            }
        };
    
        try {
            const chatResponse = await ChatService.create(chatData);
        
            const messageData = {
                sender: {
                    id: message.senderId
                },
                content: message.content,
                chat: {
                    id: chatResponse.data
                }
            };
    
            const messageResponse = await MessageService.create(messageData);
            navigate(`/chat/${chatResponse.data}`);
        } catch (error) {
            const errors = error.response.data.errors;
    
            if (errors) {
                errors.forEach((error, index) => {
                    if (error.error === "EXISTING_CHAT") {
                        toast.error("You have already responded to that advert", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
                    }
                });
            }
        }
    };
    
    return (
        <div className={`container-fluid ${style.contactLandlordContainer}`}>
          <form onSubmit={sendMessage} className="row">
            <div className="col-12 mb-3">
              <textarea
                value={message.content || ''}
                name="content"
                rows={15}
                placeholder="Your message here..."
                className="form-control"
                onChange={handleInputChange}
              />
            </div>
            <div className="col-12">
              <button type="submit" className="btn btn-primary w-100">
                Send Message
              </button>
            </div>
          </form>
        </div>
      );
      
}

export default ContactLandlordPage