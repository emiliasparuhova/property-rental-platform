import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import GeocodingService from "../services/GeocodingService";
import "./AdvertsMap.module.css"
import style from "leaflet/dist/leaflet.css"
import L from 'leaflet';
import { useNavigate } from "react-router-dom";

const AdvertsMap = (props) => {
  const [adverts, setAdverts] = useState([]);
  const navigate = useNavigate()

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

  const getAdvertCoordinates = async (city, street, zipcode) => {
    const address = `${street}, ${city}`;

    try {
      const response = await GeocodingService.getCoordinatesByAddress(address);

      return response.data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const getCityCoordinates = async (city) => {
    try {
      const response = await GeocodingService.getCoordinatesByAddress(city);

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
  }

  useEffect(() => {
    const fetchAdvertsCoordinates = async () => {
      const updatedAdverts = [];

      for (const advert of props.adverts) {
        const { city, street, zipcode } = advert.property.address;

        try {
          const coordinates = await getAdvertCoordinates(city, street, zipcode);

          console.log(city + " coordinates: " + coordinates.longitude)

          updatedAdverts.push({
            ...advert,
            latitude: coordinates.latitude,
            longitude: coordinates.longitude,
          });
        } catch (error) {
          console.error(`Error fetching coordinates for advert ${advert.id}:`, error);
        }
      }
      setAdverts(updatedAdverts);
    };

    if (props.adverts) {
      console.log(props.adverts)
      fetchAdvertsCoordinates();
    }

    if (props.selectedCity && props.selectedCity.label) {
      getCityCoordinates(props.selectedCity.label)
    }
    else {
      setViewport(initialViewport)
    }
  }, [props.adverts]);

  const navigateToAdvertPage = (id) => {
    navigate(`/adverts/${id}`)
  }

  return (
    <MapContainer
      key={`${viewport.lat}-${viewport.lng}-${viewport.zoom}`}
      className={`${style.mapContainer} vh-100 mt-5 z-0`}
      center={[viewport.lat, viewport.lng]}
      zoom={viewport.zoom}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      />
      {adverts.map((advert) => (
        advert.longitude !== 0 && advert.latitude !== 0 && (
          <Marker
            key={advert.id}
            position={[advert.latitude, advert.longitude]}
            icon={customIcon}
            eventHandlers={{
              click: () => navigateToAdvertPage(advert.id),
            }}
          >
          </Marker>
        )
      ))}
    </MapContainer>
  );
};

export default AdvertsMap;
