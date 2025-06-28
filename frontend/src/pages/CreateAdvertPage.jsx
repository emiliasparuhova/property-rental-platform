import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import CountryService from "../services/CountryService";
import Select from 'react-select';
import UserService from "../services/UserService";
import AdvertService from "../services/AdvertService";
import style from "./CreateAdvertPage.module.css"
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function CreateAdvertPage(){
  const accessToken = useSelector((state) => state.auth.token);
  const navigate = useNavigate();

  const [cities, setCities] = useState([]);

  const initialAdvertState = {
    price: 0,
    description: "",  
    property: {
      size: 0,
      numberOfRooms: 0,
      propertyType: null,
      furnishingType: null,
      address: {
        city: {
          id: "",
          label: ""
      },
        street: "",
        zipcode: ""
      }
    },
    creator: {
      "id": 0
    },
    utilitiesIncluded: null,
    availableFrom: null,
    photos: []
  };

  const [advert, setAdvert] = useState(initialAdvertState);
  const [userId, setUserId] = useState(0);

  const fetchUser = async () => {
    try {
      if (accessToken) {
        const response = await UserService.getUserByToken();

        if(response.data.role !== "LANDLORD"){
          navigate("/")
        }

        setUserId(response.data.id);
      }
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  useEffect(() => {
    if (!accessToken) {
      navigate("/login");
    }

    CountryService.getCitiesInTheNetherlands()
      .then(response => {
        setCities(response.data);
      })
      .catch(error => {
        console.error(error);
      });

    fetchUser();
  }, []);

  const saveAdvert = async (e) => {
    e.preventDefault();

    const encodedPhotos = await Promise.all(advert.photos.map(async (photo) => {
      const base64String = await arrayBufferToBase64(photo);
      return base64String;
    }));

    const data = {
      price: advert.price,
      description: advert.description,
      property: {
        size: advert.property.size,
        numberOfRooms: advert.property.numberOfRooms,
        propertyType: advert.property.propertyType,
        furnishingType: advert.property.furnishingType,
        address: {
          city: advert.property.address.city.label,
          street: advert.property.address.street,
          zipcode: advert.property.address.zipcode
        }
      },
      creator: {
        id: userId
      },
      utilitiesIncluded: advert.utilitiesIncluded,
      availableFrom: advert.availableFrom,
      photos: encodedPhotos
    };

    console.log(data)

    try{
      const response = await AdvertService.create(data)

      console.log(response)

      toast.success("Advert created", { position: toast.POSITION.BOTTOM_CENTER, toastId: "create_success" });
      navigate("/my-adverts")
    } catch (error) {
      console.error(error)

      const errors = error.response.data.errors;

          if(errors)
          {
            if (error.response.data.status === 400) {
                errors.forEach((error, index) => {
                  toast.error(error.error, { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
                }
            )}
            else{
              toast.error("Unexpected error ocurred", { position: toast.POSITION.BOTTOM_CENTER, toastId: index.toString(), draggable: false });
            }
        }
    }

  };

  const arrayBufferToBase64 = (buffer) => {
  return new Promise((resolve, reject) => {
    const blob = new Blob([buffer], { type: 'application/octet-binary' });
    const reader = new FileReader();

    reader.onloadend = () => {
      const base64String = reader.result.split(",")[1];
      resolve(base64String);
    };

    reader.onerror = reject;
    reader.readAsDataURL(blob);
  });
};

  const handleInputChange = e => {
    const { name, value } = e.target;
    setAdvert(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleNestedInputChange = e => {
    const { name, value } = e.target;
    setAdvert(prevState => ({
      ...prevState,
      property: {
        ...prevState.property,
        [name]: value
      }
    }));
  };

  const handleAddressInputChange = e => {
    const { name, value } = e.target;
    setAdvert(prevState => ({
      ...prevState,
      property: {
        ...prevState.property,
        address: {
          ...prevState.property.address,
          [name]: value
        }
      }
    }));
  };

  const handleCityInputChange = (selectedOption) => {
    setAdvert((prevState) => ({
      ...prevState,
      property: {
        ...prevState.property,
        address: {
          ...prevState.property.address,
          city: selectedOption || null,
        },
      },
    }));
  };
  

  const handleUtilitiesInputChange = e => {
    const { value } = e.target;
    setAdvert(prevState => ({
      ...prevState,
      utilitiesIncluded: value === 'Yes' ? true : value === 'No' ? false : null
    }));
  };

  const handlePhotoInputChange = async (e) => {
    const { files } = e.target;
  
    if (files.length > 0) {
      const newPhotos = [];
  
      const convertFileToByteArray = (file) => {
        return new Promise((resolve, reject) => {
          const reader = new FileReader();
  
          reader.onloadend = () => {
            const base64String = reader.result.split(",")[1];
            const byteCharacters = atob(base64String);
            const byteNumbers = new Array(byteCharacters.length);
  
            for (let i = 0; i < byteCharacters.length; i++) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
  
            const byteArray = new Uint8Array(byteNumbers);
            resolve(byteArray);
          };
  
          reader.onerror = reject;
          reader.readAsDataURL(file);
        });
      };
  
      await Promise.all(Array.from(files).map(async (file) => {
        const byteArray = await convertFileToByteArray(file);
        newPhotos.push(byteArray);
      }));
  
      setAdvert((prevState) => ({
        ...prevState,
        photos: [...prevState.photos, ...newPhotos],
      }));
    }
  };
  

  const createDataUrl = (byteArray) => {
    const blob = new Blob([byteArray], { type: 'image/*' });
    return URL.createObjectURL(blob);
  };

  const handleRemovePhoto = (index) => {
    setAdvert(prevState => ({
      ...prevState,
      photos: prevState.photos.filter((_, i) => i !== index)
    }));
  };
  
    
  return (
    <div className={`container mt-5 ${style.createAdvertContainer}`}>
      <form onSubmit={saveAdvert}>
        <div className="row">
          <div className="col-md-6">
              <input
                type="number"
                name="price"
                value={advert.price || ''}
                onChange={handleInputChange}
                className="form-control"
                placeholder="Price"
              />
    
              <textarea
                type="text"
                name="description"
                value={advert.description || ''}
                onChange={handleInputChange}
                className="form-control"
                placeholder="Description (max. 3000 characters)"
              />
    
              <input
                type="number"
                name="size"
                value={advert.property.size || ''}
                onChange={handleNestedInputChange}
                className="form-control"
                placeholder="Size m2"
              />
    
              <input
                type="number"
                name="numberOfRooms"
                value={advert.property.numberOfRooms || ''}
                onChange={handleNestedInputChange}
                className="form-control"
                placeholder="Number of Rooms"
              />
    
              <select
                className="form-select"
                name="propertyType"
                value={advert.property.propertyType || ''}
                onChange={handleNestedInputChange}
              >
                <option value="">Select Property Type</option>
                <option value="ROOM">Room</option>
                <option value="APARTMENT">Apartment</option>
                <option value="STUDIO">Studio</option>
                <option value="HOUSE">House</option>
              </select>
          </div>
    
          <div className="col-md-6">
              <select
                className="form-select"
                name="furnishingType"
                value={advert.property.furnishingType || ''}
                onChange={handleNestedInputChange}
              >
                <option value="">Select Furnishing Type</option>
                <option value="FURNISHED">Furnished</option>
                <option value="UNFURNISHED">Unfurnished</option>
                <option value="UNCARPETED">Uncarpeted</option>
              </select>

              <Select
                options={cities.map(city => ({ value: city.id, label: city.name }))}
                value={advert.property.address.city}
                name="city"
                onChange={handleCityInputChange}
                isSearchable={true}
                isClearable={true}
                placeholder="City"
                className={`${style.selectCity}`}
                id="citySelect"
              />

              <input
                type="text"
                name="street"
                value={advert.property.address.street || ''}
                onChange={handleAddressInputChange}
                className="form-control"
                placeholder="Street"
              />

              <input
                type="text"
                name="zipcode"
                value={advert.property.address.zipcode || ''}
                onChange={handleAddressInputChange}
                className="form-control"
                placeholder="Zipcode"
              />
    
            <label className={`mb-2 w-100 ${style.radioButtons}`}>
              <b>Utilities Included</b>
              <div className="d-flex">
                <div className="form-check form-check-inline">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="utilitiesIncluded"
                    value="Yes"
                    checked={advert.utilitiesIncluded === true}
                    onChange={handleUtilitiesInputChange}
                  />
                  <label className="form-check-label">Yes</label>
                </div>
                <div className="form-check form-check-inline ml-2">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="utilitiesIncluded"
                    value="No"
                    checked={advert.utilitiesIncluded === false}
                    onChange={handleUtilitiesInputChange}
                  />
                  <label className="form-check-label">No</label>
                </div>
              </div>
            </label>
    
            <label>
              <b>Available From</b>
              <input
                type="date"
                name="availableFrom"
                value={advert.availableFrom || ''}
                onChange={handleInputChange}
                className="form-control"
              />
            </label>
          </div>
        </div>
    
        <div className="row">
          <div className="col-md-12">
              <input
                type="file"
                name="photos"
                accept="image/*"
                multiple
                onChange={handlePhotoInputChange}
                className="form-control"
              />
    
              <div className="d-flex flex-row flex-wrap justify-content-center mt-5">
              {advert.photos.map((photo, index) => (
                <div key={index} className="ms-5 mb-2">
                  <img
                    src={createDataUrl(photo)}
                    className={`${style.propertyPhoto}`}
                    alt={`Photo ${index + 1}`}
                  />
                  <img
                    src="/images/remove_icon.png"
                    className={`${style.removeImage}`}
                    alt="Remove"
                    onClick={() => handleRemovePhoto(index)}
                  />
                </div>
              ))}
            </div>

          </div>
        </div>
    
        <button type="submit" className="mt-5">Create</button>
      </form>
    </div>
  );
  
}

export default CreateAdvertPage;