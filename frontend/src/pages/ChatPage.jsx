import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import UserService from "../services/UserService";
import ChatService from "../services/ChatService";
import { useEffect, useState } from "react";
import { Client } from '@stomp/stompjs';
import MessageService from "../services/MessageService";
import Message from "../components/Message";
import style from './ChatPage.module.css'
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import webSocketConfig from '../webSocketConfig'


function ChatPage(){
    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate();

    const [stompClient, setStompClient] = useState();
    const [messagesReceived, setMessagesReceived] = useState([]);

    const [user, setUser] = useState(null);

    const initialChatState = {
        id: null,
        advert: {
            id: null,
            photos: [],
            price: null,
            property: {
                address: {
                    city: "",
                    street: "",
                    zipcode: "",
                },
                id: null,
                propertyType: "",
            },
        },
        landlord: {
            id: null,
        },
        messages: [],
        tenant: {
            id: null,
        },
    };
    

    const [chat, setChat] = useState(initialChatState)

    const initialMessageState = {
        senderId: 0,
        recipientId: 0,
        content: "",
        chatId: 0
    }

    const [currentMessage, setCurrentMessage] = useState(initialMessageState)

    const { id } = useParams();
    
    useEffect(() => {
        if (!accessToken) {
            navigate("/login");
        }

        fetchData();

        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, []);

    const fetchData = async () => {
        try {
            const [user, chat] = await Promise.all([fetchUser(), fetchChat()]);
            await setupStompClient(user, chat);
        } catch (error) {
            console.error('Error fetching user or chat data:', error);
        }
    };
    
    const fetchUser = async () => {
        try {
            if (accessToken) {
                const response = await UserService.getUserByToken();
                setUser(response.data);
                setCurrentMessage((prevMessage) => ({ ...prevMessage, senderId: response.data.id }));
                return response;
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };
    
    const fetchChat = async () => {
        try {
            const response = await ChatService.get(id);
    
            setChat(response.data);
            setCurrentMessage((prevMessage) => ({ ...prevMessage, chatId: response.data.id }));
    
            return response;
        } catch (error) {
            console.error('Error fetching chat data:', error);
            
            if (error.response && error.response.status === 403) {
                toast.error(`Unauthorized access to chat`, { position: toast.POSITION.BOTTOM_CENTER, draggable: false });
                navigate("/")
            }
        }
    };
    
    const setupStompClient = async (userResponse, chatResponse) => {
        try {
            const stompClient = new Client({
                brokerURL: webSocketConfig.url,
                reconnectDelay: 5000,
                heartbeatIncoming: 4000,
                heartbeatOutgoing: 4000,
            });

            const senderId = userResponse.data.id;
            const recipientId = userResponse.data.id === chatResponse.data.tenant.id ? chatResponse.data.landlord.id : chatResponse.data.tenant.id
            setCurrentMessage((prevMessage) => ({ ...prevMessage, recipientId: recipientId }));

    
            stompClient.onConnect = () => {

                stompClient.subscribe(`/user/${chatResponse.data.id}/queue/inboxmessages`, (data) => {
                    onMessageReceived(data);       
                });
            };
    
            stompClient.activate();
            setStompClient(stompClient);
        } catch (error) {
            console.error(error);
        }
    };
    

    const handleInputChange = e => {
        const { name, value } = e.target;
        setCurrentMessage(prevState => ({
            ...prevState,
            [name]: value
        }));
        };

    const sendMessage = async () => {
        console.log(currentMessage.senderId)

        const messageToPublish = {
            sender: {
                id: currentMessage.senderId,
            },
            chat: {
                id: currentMessage.chatId
            },
            content: currentMessage.content
        }

        stompClient.publish({
            destination: `/app/send/${chat.id}`,
            body: JSON.stringify(messageToPublish),
        });        

        const messageData = {
            sender: {
                id: currentMessage.senderId
            },
            content: currentMessage.content,
            chat: {
                id: currentMessage.chatId
            }
        }

        const messageResponse = await MessageService.create(messageData)

        setCurrentMessage(prevState => ({
            ...prevState,
            content: ""
        }));
    };

    const onMessageReceived = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    const convertPhoto = (photo) => {
        if (photo)
        {
            return `data:image/jpeg;base64,${photo}`
        }
        return null
    }

    const navigateToAdvertDetails = () => {
        navigate(`/adverts/${chat.advert.id}`)
    }

    return (
        <div className={`container ${style.chatContainer} mt-5 text-break`}>
            <div>
                <div className={`d-flex flex-row mb-3 ${style.advertContainer}`} onClick={navigateToAdvertDetails}>
                    <div className="me-3">
                        {chat.advert.photos.length > 0 && chat.advert.photos[0] ? (
                        <img className={`${style.advertImage}`} src={convertPhoto(chat.advert.photos[0])} alt="Advert" />
                    ) : (
                        <img className={`${style.advertImage}`} src="/images/no_image_available_image.jpg" alt="Default" />
                    )}
                    </div>
                    <div className="">
                        <p className="m-0 mt-1 fw-bold">{chat.advert.property.address.city}</p>
                        <p className="m-0 small fst-italic">{chat.advert.property.address.street} {chat.advert.property.address.zipcode}</p>
                        <p className="m-0 small fst-italic">€{chat.advert.price}</p>
                    </div>
                </div>
                <div className={style.scrollableContainer}>
                    {chat && chat.messages && chat.messages.length > 0 && (
                        <div className="d-flex flex-column">
                        {chat.messages.map((message) => (
                            <div key={message.id} className={`${message.sender.id === currentMessage.senderId ? style.senderMessage : style.recipientMessage}`}>
                            <Message message={message} isAuthor={message.sender.id === currentMessage.senderId} />
                            </div>
                        ))}
                        </div>
                    )}

                    {messagesReceived && messagesReceived.length > 0 && (
                        <div className="d-flex flex-column">
                        {messagesReceived.map((message, index) => (
                            <div key={index} className={`${message.sender.id === currentMessage.senderId ? style.senderMessage : style.recipientMessage}`}>
                            <Message message={message} isAuthor={message.sender.id === currentMessage.senderId} />
                            </div>
                        ))}
                        </div>
                    )}
                </div>

                <div className={`row justify-content-center align-content-center bottom-0 ${style.messageBar}`}>
                    <div className="d-flex flex-row align-items-center">
                        <textarea
                            value={currentMessage.content || ''}
                            name="content"
                            rows={2}
                            placeholder="Message..."
                            className="form-control me-3 mb-3 col"
                            onChange={handleInputChange}
                        />
                        <button 
                            className="col-md-3 col-sm-3"
                            onClick={sendMessage}
                        >
                            Send
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
      
}


export default ChatPage