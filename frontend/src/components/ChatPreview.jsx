import { useEffect, useState } from "react";
import style from './ChatPreview.module.css'
import { useNavigate } from "react-router-dom";


function ChatPreview(props){
    const navigate = useNavigate()

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
            profilePicture: ""
        },
        messages: [],
        tenant: {
            id: null,
            profilePicture: ""
        },
    };
    

    const [chat, setChat] = useState(initialChatState)
    const [currentUserId, setCurrentUserId] = useState(0)
    const [recipientPicture, setRecipientPicture] = useState(0)
    const [receivedMessage, setReceivedMessage] = useState(null)

    useEffect(() => {
        if(props.chat){
            setChat(props.chat)
        }

        if(props.userId){
            setCurrentUserId(props.userId)

            const recipientPicture = props.userId === props.chat.landlord.id ? props.chat.tenant.profilePicture : props.chat.landlord.profilePicture
            setRecipientPicture(recipientPicture)
        }

        if(props.receivedMessage){
            setReceivedMessage(props.receivedMessage)
        }
    })

    const convertPhoto = (photo) => {
        if (photo)
        {
            return `data:image/jpeg;base64,${photo}`
        }
        return "/images/default_pfp.png";
    }

    const formatTimestamp = (message) => {
        const timestamp = new Date(message.timestamp);
        const hours = timestamp.getHours();
        const minutes = timestamp.getMinutes();

        const formattedTimestamp = `${hours < 10 ? '0' : ''}${hours}:${minutes < 10 ? '0' : ''}${minutes} ${timestamp.toLocaleDateString()}`;

        return formattedTimestamp
    }

    const getMessagePreview = (message) => {
        const words = message.split(' ');
        const previewWords = words.slice(0, Math.min(10, words.length));
      
        if (words.length > 10) {
          previewWords.push("...");
        }
      
        return previewWords.join(' ');
    };

    const navigateToChatPage = () => {
        navigate(`/chat/${chat.id}`)
    }

    return(
        <div className={`container mb-5 ${style.chatPreviewContainer}`} onClick={navigateToChatPage}>
            <div className="row d-flex flex-row align-items-center">
                <div className="col-md-3 d-flex justify-content-end">
                <img
                    src={convertPhoto(recipientPicture)}
                    alt="Profile Picture"
                    className={`img-fluid rounded-circle ${style.profilePicture}`}
                />
                </div>
                <div className="col-md-9 mt-3">
                    <div className="row">
                        <div className="col mb-2">
                            <p className="m-0 small fw-bold">{chat.advert.property.address.city} {chat.advert.property.address.street} {chat.advert.property.address.zipcode} | €{chat.advert.price}</p>
                        </div>
                        <hr></hr>
                        <div className="col text-secondary">
                        {receivedMessage ? (
                            <div className="text-dark">
                                <p><b><em>{receivedMessage.sender.name}:</em> {getMessagePreview(receivedMessage.content)}</b></p>
                                <p className="small fst-italic"><b>{formatTimestamp(receivedMessage)}</b></p>
                            </div>
                        ) : (
                            <>
                                {chat.messages.length > 0 && (
                                    <div>
                                        <p><em>{chat.messages[0].sender.name}:</em> {getMessagePreview(chat.messages[0].content)}</p>
                                        <p className="small fst-italic">{formatTimestamp(chat.messages[0])}</p>
                                    </div>
                                )}
                            </>
                        )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}

export default ChatPreview;