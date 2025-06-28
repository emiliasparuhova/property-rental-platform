import { useEffect, useState } from "react";
import style from './Advert.module.css'
import { useNavigate } from "react-router-dom";


function Advert(props){

    const [advert, setAdvert] = useState(null)
    const navigate = useNavigate()

    useEffect( () => {
        if (props.advert){
            setAdvert(props.advert)
        }
    }, [])

    const showAdvertDetails = () => {
        navigate(`/adverts/${advert.id}`)
    }

    const convertPhoto = (photo) => {
      if (photo)
      {
          return `data:image/jpeg;base64,${photo}`
      }
      return null
    }

    const getDescriptionPreview = (description) => {
      const words = description.split(' ');
      const previewWords = words.slice(0, Math.min(45, words.length));

      if (words.length > 45) {
        previewWords.push("...");
      }
    
      return previewWords.join(' ');
    };

    return (
      <div className={`row ${style.advertContainer}`} onClick={showAdvertDetails}>
        {advert && (
          <div className="row">
            <div className="col-md-5 d-flex justify-content-center mb-3" key={advert.id}>
              {advert.photos.length > 0 && advert.photos[0] ? (
                <img className={`${style.advertImage}`} src={convertPhoto(advert.photos[0])} alt="Advert" loading="lazy"/>
              ) : (
                <img className={`${style.advertImage}`} src="/images/no_image_available_image.jpg" alt="Default" loading="lazy"/>
              )}
            </div>
            <div className="col-md-7">
              <div className={`row mb-3 ${style.advertAddress}`}>
                <div className="col">
                  <h4>{advert.property.address.city}</h4>
                  <p><b>{advert.property.address.street}, {advert.property.address.zipcode}</b></p>
                </div>
                <div className="col ms-2">
                  <p><b>€{advert.price}</b> p/m</p>
                  <p><b>{advert.property.furnishingType}</b></p>
                </div>
              </div>
              {advert.description && (
                <p>{getDescriptionPreview(advert.description)}</p>
              )}
            </div>
          </div>
        )}
      </div>
    );
    
}

export default Advert;