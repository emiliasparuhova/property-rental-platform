import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import style from './AdvertCard.module.css'


function AdvertCard(props) {
    const [advert, setAdvert] = useState(null)
    const navigate = useNavigate()

    useEffect(() => {
        if (props.advert) {
            setAdvert(props.advert)
        }
    }, [])

    const showAdvertDetails = () => {
        navigate(`/adverts/${advert.id}`)
    }

    const convertPhoto = (photo) => {
        if (photo) {
            return `data:image/jpeg;base64,${photo}`
        }
        return null
    }

    return (
        <div className={`row ${style.advertContainer}`} onClick={showAdvertDetails}>
            {advert && (
                <div className="row d-flex flex-column justify-content-center align-items-center text-center">
                    <div className="col-md-5 d-flex justify-content-center mb-3" key={advert.id}>
                        {advert.photos.length > 0 && advert.photos[0] ? (
                            <img className={`${style.advertImage}`} src={convertPhoto(advert.photos[0])} alt="Advert" loading="lazy" />
                        ) : (
                            <img className={`${style.advertImage}`} src="/images/no_image_available_image.jpg" alt="Default" loading="lazy" />
                        )}
                    </div>
                    <div className="col">
                        <p className="h5">{advert.property.address.city}</p>
                        <p className="small fw-light fst-italic"><b>{advert.property.address.street}</b></p>
                    </div>
                </div>
            )}
        </div>
    );
}

export default AdvertCard;