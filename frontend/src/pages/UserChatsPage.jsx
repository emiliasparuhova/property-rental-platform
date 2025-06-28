import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import UserService from "../services/UserService";
import ChatService from "../services/ChatService";
import ChatPreview from "../components/ChatPreview";
import style from './UserChatsPage.module.css'
import { Client } from '@stomp/stompjs';
import webSocketConfig from '../webSocketConfig'


function UserChatsPage(){
    const accessToken = useSelector((state) => state.auth.token);
    const navigate = useNavigate()

    const [chats, setChats] = useState([])
    const [user, setUser] = useState(null)
    const [stompClient, setStompClient] = useState();
    const [receivedMessages, setReceivedMessages] = useState([])


    useEffect(() => {
        if (!accessToken) {
            navigate("/login");
        }

        const fetchData = async () => {
          try {
            const userId = await fetchUser();
            const chatsResponse = await fetchChats(userId);
            setupStompClient(chatsResponse);
          } catch (error) {
            console.error(error);
          }
        };
        
        fetchData();
        
    }, []);

    const fetchChats = async (userId) => {
        try{
            const response = await ChatService.getChatsByUser(userId)
            setChats(response.data)

            return response
        } catch (error){
            console.error(error)
        }
    }

    const fetchUser = async () => {
        try {
            if (accessToken) {
                const response = await UserService.getUserByToken();
                setUser(response.data);
                return response.data.id;
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    const setupStompClient = async (chatsResponse) => {
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
              onMessageReceived(data);
            });
          });
          };
    
        stompClient.activate();
        setStompClient(stompClient);
      } catch (error) {
        console.error(error);
      }
    };
    

    const onMessageReceived = (data) => {
      const message = JSON.parse(data.body);
    
      const existingChatIndex = receivedMessages.findIndex(existingChat => existingChat.chatId === message.chat.id);
    
      if (existingChatIndex !== -1) {
        const updatedReceivedMessages = [...receivedMessages];
        updatedReceivedMessages[existingChatIndex] = {
          chatId: receivedMessages[existingChatIndex].chatId,
          lastMessage: message
        };
        setReceivedMessages(updatedReceivedMessages);
      } else {
        setReceivedMessages([...receivedMessages, { chatId: message.chat.id, lastMessage: message }]);
      }
    };
    

    const getChatLastMessage = (chatId) => {
      const messagesForChat = receivedMessages.find(receivedMessage => receivedMessage.chatId === chatId);

      if (messagesForChat) {
        const lastMessage = messagesForChat.lastMessage;
        console.log(lastMessage);
        return lastMessage;
      }
    
      return null;
    };
    
    return (
        <div className={`mt-5 ${style.chatsContainer}`}>
          <h4>Chats</h4>
          <hr className="mb-4"></hr>
          {chats && chats.length > 0 ? (
            <div>
              {chats.map(chat => (
                <div key={chat.id}>
                  <ChatPreview chat={chat} userId={user.id} receivedMessage={getChatLastMessage(chat.id)} />
                </div>
              ))}
            </div>
          ) : (
            <p>No Chats</p>
          )}
        </div>
    );
}


export default UserChatsPage;