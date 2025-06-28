import { useEffect, useState } from "react";
import LinkedAccountService from "../services/LinkedAccountService";
import style from "./LinkedAccount.module.css"

function LinkedAccount(props){
    const initialLinkedAccountState = {
        id: 0,
        user: {
            id: 0
        },
        externalAccount: {
            id: "",
            email: "",
            name: "",
            provider: ""
        }
    }

    const [linkedAccount, setLinkedAccount] = useState(initialLinkedAccountState)

    useEffect(() => {
        if(props.linkedAccount){
            setLinkedAccount(props.linkedAccount)
        }
    }, [])

    const removeLinkedAccount = async () => {
        try{
            const id = linkedAccount.id;
            const response = await LinkedAccountService.remove(id)

            props.onRemoveLinkedAccount(id);
        } catch (error){
            console.error(error)
        }
    }

    return(
        <div className={`${style.linkedAccountContainer} container d-flex flex-column text-center`}>
            <div className="row">
                <div className="col">
                    <img
                        src="/images/google_icon.png"
                        alt="google icon"
                        className={`${style.providerIcon} me-5 mt-1`}
                    />
                    </div>
                <div className="col-md-8 d-flex flex-row">
                    <p className="me-5 mt-3">{linkedAccount.externalAccount.email}</p>
                    <p className="me-5 mt-3">{linkedAccount.externalAccount.name}</p>
                </div>
                <div className="col">
                    <img
                        src="/images/cross_icon.png"
                        onClick={removeLinkedAccount} 
                        className={`${style.removeIcon} mt-3`}
                    />
                </div>
            </div>
        </div>
    );

}

export default LinkedAccount;