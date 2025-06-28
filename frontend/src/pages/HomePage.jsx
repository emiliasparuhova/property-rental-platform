import SearchBar from "../components/SearchBar";
import style from "./HomePage.module.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import { React, useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import CountryService from "../services/CountryService";
import { useNavigate } from "react-router-dom";
import AdvertService from "../services/AdvertService";
import AdvertCard from "../components/AdvertCard";
import Carousel from 'react-bootstrap/Carousel';

function HomePage() {
  const [cities, setCities] = useState([]);
  const [selectedCity, setSelectedCity] = useState(null);
  const [mostPopularAdverts, setMostPopularAdverts] = useState([])
  const [itemsPerSlide, setItemsPerSlide] = useState(3);

  const accessToken = useSelector((state) => state.auth.token);
  const navigate = useNavigate();

  const mostPopularAdvertsCount = 6;

  const handleCityChange = (selectedOption) => {
    setSelectedCity(selectedOption);
  };

  const handleButtonClick = () => {
    navigate('/adverts', { state: { city: selectedCity } });
  }

  useEffect(() => {
    CountryService.getCitiesInTheNetherlands()
      .then(response => {
        setCities(response.data);
      })
      .catch(error => {
        console.error(error);
      });

    getMostPopularAdverts(mostPopularAdvertsCount)
  }, []);

  useEffect(() => {
    if (accessToken) {
      console.log('User is authenticated. Token:', accessToken);
    }
  }, [accessToken])

  const handleResize = () => {
    if (window.innerWidth < 576) {
      setItemsPerSlide(1);
    } else if (window.innerWidth < 768) {
      setItemsPerSlide(2);
    } else {
      setItemsPerSlide(3);
    }
  };

  useEffect(() => {
    handleResize();
    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  const getMostPopularAdverts = async (advertCount) => {
    try {
      const response = await AdvertService.getMostPopular(advertCount)

      setMostPopularAdverts(response.data)

    } catch (error) {
      console.error(error);
    }
  }

  const renderCarouselItems = () => {
    const items = [];

    for (let i = 0; i < mostPopularAdverts.length; i += itemsPerSlide) {
      const slideItems = mostPopularAdverts.slice(i, i + itemsPerSlide).map((advert) => (
        <div className="col d-flex justify-content-center" key={advert.id}>
          <AdvertCard advert={advert} />
        </div>
      ));

      items.push(
        <Carousel.Item key={i}>
          <div className="row">{slideItems}</div>
        </Carousel.Item>
      );
    }

    return items;
  };

  return (
    <div className={`container-fluid text-center p-0 ${style.homeContainer}`}>
      <div className={`row justify-content-center d-flex flex-column align-items-center`}>
        <div className="col">
          <SearchBar cities={cities} handleCityChange={handleCityChange} />
        </div>
        <div className='col'>
          <button className='mt-3' onClick={handleButtonClick}>Search</button>
        </div>
      </div>
      <div className="row justify-content-center mt-5">
        {mostPopularAdverts && (
          <>
            <div className="row d-flex text-start">
              <p className="col h4">Most Trending</p>
            </div>
            <hr className="mt-3 mb-4"></hr>
            <Carousel className="vw-100">
              {mostPopularAdverts.length > 0 && renderCarouselItems()}
            </Carousel>
          </>
        )}
      </div>
    </div>
  );
}

export default HomePage;