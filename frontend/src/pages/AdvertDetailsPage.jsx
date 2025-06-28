import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AdvertService from "../services/AdvertService";
import style from './AdvertDetailsPage.module.css'
import Carousel from 'react-bootstrap/Carousel';
import UserService from "../services/UserService";
import { useSelector } from "react-redux";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Heart from "react-heart"
import FavouriteAdvertService from "../services/FavouriteAdvertService";
import GeocodingService from "../services/GeocodingService";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import "leaflet/dist/leaflet.css"
import L from 'leaflet';

function AdvertsDetailsPage() {
  const accessToken = useSelector((state) => state.auth.token);
  const navigate = useNavigate()

  const initialAdvertState = {
    id: "",
    price: "",
    description: "",
    property: {
      size: "",
      numberOfRooms: "",
      propertyType: null,
      furnishingType: null,
      address: {
        city: "",
        street: "",
        zipcode: ""
      }
    },
    creator: {
      id: "",
      name: "",
      description: "",
      profilePicture: "",
      joinDate: ""
    },
    utilitiesIncluded: null,
    availableFrom: "",
    creationDate: "",
    photos: []

  }

  const initialUserState = {
    id: 0,
    role: null
  }

  const initalCoordinatesState = {
    longitude: 0,
    latitude: 0
  }

  const [advert, setAdvert] = useState(initialAdvertState)
  const [advertPictures, setAdvertsPictures] = useState(null)
  const [landlordPicture, setLandlordPicture] = useState(null)
  const [user, setUser] = useState(initialUserState)
  const [landlordResponseRate, setLandlordResponseRate] = useState(0);
  const [isAdvertFavourite, setIsAdvertFavourite] = useState(null)
  const [isOpen, setIsOpen] = useState(false);
  const [coordinates, setCoordinates] = useState(initalCoordinatesState);

  const { id } = useParams();

  const initialViewport = {
    lat: 52.3676,
    lng: 4.9041,
    zoom: 7,
  };

  const customIcon = new L.Icon({
    iconUrl: "/images/location_marker_icon.png",
    iconSize: [52, 35],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32],
  });

  const [viewport, setViewport] = useState(initialViewport);

  const getAdvert = async () => {
    const advertId = id
    try {
      const advertResponse = await AdvertService.get(advertId)

      if (advertResponse.data) {
        setAdvert(advertResponse.data)
        getAdvertCoordinates(advertResponse.data.property.address.city, advertResponse.data.property.address.street)

        const landlordStats = await UserService.getLandlordResponseRate(advertResponse.data.creator.id)
        setLandlordResponseRate(landlordStats.data)
      }
    } catch (error) {
      console.error(error)
    }
  }

  const getAdvertCoordinates = async (city, street) => {
    const address = `${street}, ${city}`;

    try {
      const response = await GeocodingService.getCoordinatesByAddress(address);

      setCoordinates(response.data)

      const newViewport = {
        lat: response.data.latitude,
        lng: response.data.longitude,
        zoom: 13,
      }

      setViewport(newViewport)
    } catch (error) {
      console.error(error);
      throw error;
    }
  };  

  const fetchUser = async () => {
    try {
      if (accessToken) {
        const response = await UserService.getUserByToken();
        setUser(response.data);

        const userId = response.data.id

        const data = {
          userId: userId,
          advertId: id
        }

        if (response.data.role === "TENANT") {
          const isFavouriteResponse = await FavouriteAdvertService.getIsAdvertFavourite({
            params: data
          })
          setIsAdvertFavourite(isFavouriteResponse.data)
        }
      }
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const deleteAdvert = async () => {
    if (advert.creator.id === user.id) {
      const advertId = advert.id

      const userConfirmed = window.confirm('Are you sure you want to delete this advert?');

      if (userConfirmed) {
        try {
          await AdvertService.remove(advertId)

          toast.success("Advert permanently removed", { position: toast.POSITION.BOTTOM_CENTER, toastId: "create_success" });
          window.history.back();

        } catch (error) {
          console.error(error);
        }
      }
    }
  }

  const addAdvertToFavourite = async (data) => {
    try {
      const response = await FavouriteAdvertService.create(data)

      console.log(response.data)

    } catch (error) {
      console.error(error)
    }
  }

  const removeAdvertFromFavourite = async (userId, advertId) => {
    try {
      const response = await FavouriteAdvertService.remove(userId, advertId)
    } catch (error) {
      console.error(error)
    }
  }

  const handleHeartClick = () => {
    const data = {
      user: {
        id: user.id
      },
      advert: {
        id: advert.id
      }
    }

    if (isAdvertFavourite) {
      removeAdvertFromFavourite(data.user.id, data.advert.id)
      setIsAdvertFavourite(false)
    } else {
      addAdvertToFavourite(data)
      setIsAdvertFavourite(true)
    }
  }

  const convertPhoto = (photo) => {
    if (photo) {
      return `data:image/jpeg;base64,${photo}`
    }
    return null
  }

  const getLandlordDescriptionPreview = (description) => {
    const words = description.split(' ');
    const previewWords = words.slice(0, Math.min(20, words.length));

    if (words.length > 20) {
      previewWords.push("...");
    }

    return previewWords.join(' ');
  };

  const navigateToLandlordsProfile = () => {
    navigate(`/users/${advert.creator.id}`)
  }

  const handleContactLandlordButtonClick = () => {
    navigate("/contact-landlord", { state: { advert: advert } })
  }

  useEffect(() => {
    getAdvert()
    fetchUser()
  }, [id, isAdvertFavourite])

  useEffect(() => {
    const convertedAdvertPhotos = advert.photos.length > 0 ? advert.photos.map(convertPhoto) : [];
    const convertedLandlordPhoto = convertPhoto(advert.creator.profilePicture)

    setAdvertsPictures(convertedAdvertPhotos)
    setLandlordPicture(convertedLandlordPhoto)
  }, [advert])

  const openPopup = () => {
    setIsOpen(true);
  };

  const closePopup = () => {
    setIsOpen(false);
  };

  const handleCopyLink = () => {
    const urlToCopy = window.location.href;

    navigator.clipboard.writeText(urlToCopy)
      .then(() => setCopySuccess(true))
      .catch(err => console.error('Unable to copy text', err));

    closePopup();
  };

  const handleShareOnWhatsApp = () => {
    const url = encodeURIComponent(window.location.href);
    const whatsappUrl = `whatsapp://send?text=${encodeURIComponent(url)}`;

    window.open(decodeURIComponent(whatsappUrl), '_blank');

    closePopup();
  };


  return (
    <div className={`container mt-5 ${style.advertContainer}`}>
      <div className="d-flex justify-content-end">
        <img
          className={style.shareIcon}
          src="/images/share_icon.png"
          alt="Share"
          onClick={isOpen ? closePopup : openPopup}
        />
        {isOpen && (
          <div className={`${style.popup}`}>
            <p onClick={handleCopyLink}>
              <img className={style.shareOptionIcon} src="/images/copy_link_icon.jpg" alt="copy link icon" />
              Copy Link
            </p>
            <p onClick={handleShareOnWhatsApp}>
              <img className={style.shareOptionIcon} src="/images/whatsapp_icon.png" alt="whatsapp icon" />
              WhatsApp
            </p>
          </div>
        )}
      </div>
      {user.role === "TENANT" && (
        <div className={`row mb-3 ${style.heartIcon}`}>
          <Heart
            isActive={isAdvertFavourite}
            onClick={handleHeartClick}
            activeColor="#012245"
            inactiveColor="#012245"
            animationTrigger="both"
          />
        </div>
      )}
      {advert.creator.id === user.id && (
        <div className="row d-flex justify-content-end mb-5 mt-3">
          <button onClick={deleteAdvert}>Remove advert</button>
        </div>
      )}
      <div className="row">
        <div className={`col-sm-7 col-md-6 col-lg-5`}>
          <div className="mb-5">
            <h3>
              {advert.property.propertyType ? `${advert.property.propertyType.charAt(0).toUpperCase()}${advert.property.propertyType.slice(1).toLowerCase()}`
                : 'Property'} for rent
            </h3>
            <h3>in {advert.property.address.city}</h3>
            <h3>at {advert.property.address.street}, {advert.property.address.zipcode}</h3>
          </div>
          <p><b>€{advert.price}</b> p/m | Utilities {advert.utilitiesIncluded ? "incl." : "excl."}</p>
          <p><b>{advert.property.furnishingType}</b></p>
          <p>Total {advert.property.numberOfRooms} room(s) | <b>{advert.property.size} m2</b></p>
          <p>Available from <b>{advert.availableFrom}</b></p>
          <p>Posted on <b>{advert.creationDate}</b></p>
        </div>

        <div className="col-sm-11 col-md-9 col-lg-7 d-flex justify-content-center">
          {advertPictures && advertPictures.length > 0 ? (
            <div>
              <Carousel interval={2000}>
                {advertPictures.map((photo, index) => (
                  <Carousel.Item key={index}>
                    <img
                      src={photo}
                      alt={`Advert ${index + 1}`}
                      className={`d-block ${style.propertyPhoto}`}
                    />
                  </Carousel.Item>
                ))}
              </Carousel>
            </div>
          ) : (
            <img
              src="/images/no_image_available_image.jpg"
              alt="Default Advert"
              className={`img-fluid ${style.propertyPhoto}`}
            />
          )}
        </div>
      </div>

      <div className="row mt-5">
        <h3 className="mb-3">Description</h3>
        <div className={`col ${style.descriptionInfo}`}>
          <p>{advert.description}</p>
        </div>
      </div>

      <div className="row mt-5">
        <h3 className="mb-5">Contact</h3>
        <div className={`col-md-12`}>
          <div className="row">
            <div className="col-md-3" onClick={navigateToLandlordsProfile}>
              {landlordPicture ? (
                <img src={landlordPicture} alt="Profile" className={`img-fluid rounded-circle ${style.landlordPicture}`} />
              ) : (
                <img src="/images/default_pfp.png" alt="Default Profile" className={`img-fluid rounded-circle ${style.landlordPicture}`} />
              )}
            </div>
            <div className="col-md-5">
              <div className="mb-3">
                <h5>{advert.creator.name}</h5>
              </div>
              {advert.creator.description ? (
                <p>Description: {getLandlordDescriptionPreview(advert.creator.description)}</p>
              ) : (
                <p><em>Landlord has no description</em></p>
              )}
              <p>Active since <b>{advert.creator.joinDate}</b></p>
              <p>Response rate: <b>{Math.ceil(landlordResponseRate)}%</b></p>
              {advert.creator.id === user.id ? (
                <button className={`mt-5 mb-5`} disabled>
                  That's you
                </button>
              ) : user.role === "LANDLORD" ? (
                <button className={`mt-5 mb-5`} disabled>
                  You cannot respond to adverts
                </button>
              ) : (
                <button onClick={handleContactLandlordButtonClick} className={`mt-5 mb-5`}>
                  Contact
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
      {advert.longitude !== 0 && advert.latitude !== 0 && (
        <div className="row mt-3 mb-5">
          <h3>Location</h3>
          <div className="col-12 d-flex justify-content-center">
            <MapContainer
              key={`${viewport.lat}-${viewport.lng}-${viewport.zoom}`}
              className={`${style.mapContainer} mt-5 z-0 mb-5 d-flex justify-content-center align-items-center`}
              center={[viewport.lat, viewport.lng]}
              zoom={viewport.zoom}
            >
              <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
              />
              <Marker
                key={advert.id}
                position={[coordinates.latitude, coordinates.longitude]}
                icon={customIcon}
              />
            </MapContainer>
          </div>
        </div>
      )}
    </div>
  );
}

export default AdvertsDetailsPage
