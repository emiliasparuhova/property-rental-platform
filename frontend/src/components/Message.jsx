import { useEffect, useState } from "react"
import style from './Message.module.css'
import { isAction } from "@reduxjs/toolkit"
import { useNavigate } from "react-router-dom"


function Message(props){
    const navigate = useNavigate();

    const [message, setMessage] = useState({
        id: 0,
        sender: {
            id: 0,
            name: '',
            profilePicture: []
        },
        content: '',
        timestamp: ''
    })

    const [userProfilePicture, setUserProfilePicture] = useState('')
    const [isMessageAuthor, setIsMessageAuthor] = useState(null)


    useEffect(() => {
        if(props.message){
            setMessage(props.message)
            setIsMessageAuthor(props.isAuthor)
            if (props.message.sender.profilePicture)
            {
                setUserProfilePicture(`data:image/jpeg;base64,${props.message.sender.profilePicture}`)
            }
        }
    })

    const formatTimestamp = (message) => {
        const timestamp = new Date(message.timestamp);
        const hours = timestamp.getHours();
        const minutes = timestamp.getMinutes();

        const formattedTimestamp = `${hours < 10 ? '0' : ''}${hours}:${minutes < 10 ? '0' : ''}${minutes} ${timestamp.toLocaleDateString()}`;

        return formattedTimestamp
    }

    const navigateToUserProfile = (id) => {
        navigate(`/users/${id}`)
    }

    return (
        <div className={`chat-bubble recipient row d-flex ${isMessageAuthor ? 'flex-row-reverse' : 'flex-row'}`}>
            <div className={`me-2 ms-2 col-md-3 ${style.pictureContainer}`} onClick={() => navigateToUserProfile(message.sender.id)}>
           { userProfilePicture ? (
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
            <div className={`col ${isMessageAuthor ? style.senderMessage : style.recipientMessage}`}>
                <div className="message-content">{message.content}</div>
                <div className="timestamp mt-3 small"><b><em>{formatTimestamp(message)}</em></b></div>
            </div>
        </div>
      );
}

export default Message