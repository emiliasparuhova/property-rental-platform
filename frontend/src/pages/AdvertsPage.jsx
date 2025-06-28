import { useLocation } from 'react-router-dom';
import AdvertService from '../services/AdvertService';
import { useEffect, useState } from 'react';
import Advert from '../components/Advert'
import SearchBar from '../components/SearchBar';
import CountryService from '../services/CountryService';
import style from './AdvertsPage.module.css'
import Carousel from 'react-bootstrap/Carousel';
import Slider from 'rc-slider';
import ReactPaginate from 'react-paginate';
import 'rc-slider/assets/index.css';
import AdvertsMap from '../components/AdvertsMap';


function AdvertsPage(){
    const location = useLocation();
    const [adverts, setAdverts] = useState([])
    const [statistics, setStatistics] = useState([])
    const [cities, setCities] = useState([])
    const [totalPages, setTotalPages] = useState(0);
    const [activeTab, setActiveTab] = useState("listView")


    const initialFilterState = {
        city: {
            id: '',
            label: null
        },
        minPrice: 0,
        maxPrice: 0,
        propertyType: '',
        furnishingType: '',
        pageNumber: 0,
        pageSize: 5
    }

    const selectedCity = location.state?.city;

    if (selectedCity){
        initialFilterState.city = selectedCity
    }

    const [filteringCriteria, setFilteringCriteria] = useState(initialFilterState);

    const handleInputChange = (field, value) => {
      setFilteringCriteria({ ...filteringCriteria, [field]: value });
    };
  
    const handleCityChange = (selectedCity) => {
      setFilteringCriteria({ ...filteringCriteria, city: selectedCity });
      setStatistics([])
    };

    const handlePageChange = (selectedPage) => {
      setFilteringCriteria({ ...filteringCriteria, pageNumber: selectedPage.selected });
    };

    const switchTab = (tab) => {
      setActiveTab(tab);
    };

    const getAllAdverts = async () => {
        const filterData = {
            city: filteringCriteria.city.label,
            minPrice: filteringCriteria.minPrice,
            maxPrice: filteringCriteria.maxPrice,
            propertyType: filteringCriteria.propertyType,
            furnishingType: filteringCriteria.furnishingType,
            pageNumber: filteringCriteria.pageNumber,
            pageSize: filteringCriteria.pageSize
        }

        const response = await AdvertService.getAll({
            params: filterData
        });
        
        if (response.data){
            setAdverts(response.data.adverts)
        }
    }

    const calculateTotalPages = async () => {
      const filterData = {
        city: filteringCriteria.city.label,
        minPrice: filteringCriteria.minPrice,
        maxPrice: filteringCriteria.maxPrice,
        propertyType: filteringCriteria.propertyType,
        furnishingType: filteringCriteria.furnishingType,
      }

      const response = await AdvertService.getAdvertsCount({
          params: filterData
      });

      const advertsCount = response.data

      const calculatedTotalPages = Math.ceil(advertsCount / filteringCriteria.pageSize);
      setTotalPages(calculatedTotalPages);
    };

    const getStatisticsByCity = async () => {
      if (filteringCriteria.city.label){
        const city = filteringCriteria.city.label;

        const response = await AdvertService.getStatisticsByCity(city)

        if (response){
          const statisticsArray = [
            `There are ${response.data.advertsCount} listings in ${city}`,
            `The average advert price in ${city} is ${Math.ceil(response.data.averageAdvertPrice)}€`,
            `The average property size in ${city} is ${response.data.averagePropertySize.toFixed(2)} m2`
          ];

          setStatistics(statisticsArray)
        }
      }
    }
    
    useEffect(() => {
      CountryService.getCitiesInTheNetherlands()
        .then(response => {
          setCities(response.data);
        })
        .catch(error => {
          console.error(error);
        });
    }, []);

    useEffect(() => {      
        getAllAdverts()
        calculateTotalPages();
    }, [filteringCriteria])


    useEffect(() => {
      getStatisticsByCity()
    }, [filteringCriteria.city])
    
    return (
      <div className={`container mt-5 ${style.advertsPageContainer}`}>
        <div className={`row mb-4 ${style.fixedRowContainer}`}>
          <div className='col-md-7 d-flex flex-column align-items-center me-3'>
            <div className='w-50 text-center'>
              <Slider
                className={`mt-5`}
                range
                min={0}
                max={10000}
                step={50} 
                value={[filteringCriteria.minPrice, filteringCriteria.maxPrice || 10000]}
                onChange={value => {
                  setFilteringCriteria({
                    ...filteringCriteria,
                    minPrice: value[0],
                    maxPrice: value[1]
                  });
                }}
              />
              <p>
                <b>Min Price: {filteringCriteria.minPrice}€ | Max Price: {filteringCriteria.maxPrice || "10 000"}€</b>
              </p>
            </div>
            <SearchBar
              cities={cities}
              city={selectedCity}
              handleCityChange={handleCityChange}
            />
          </div>
          <div className='col-md-4 mt-5'>    
            <div>
              <select
                value={filteringCriteria.propertyType || ''}
                onChange={(e) =>
                  handleInputChange('propertyType', e.target.value)
                }
                className="form-select"
              >
                <option value={''}>Property Type</option>
                <option value="ROOM">Room</option>
                <option value="STUDIO">Studio</option>
                <option value="APARTMENT">Apartment</option>
                <option value="HOUSE">House</option>
              </select>
            </div>
    
            <div className='mt-3'>
              <select
                value={filteringCriteria.furnishingType || ''}
                onChange={(e) =>
                  handleInputChange('furnishingType', e.target.value)
                }
                className="form-select"
              >
                <option value={''}>Furnishing Type</option>
                <option value="FURNISHED">Furnished</option>
                <option value="UNFURNISHED">Unfurnished</option>
                <option value="UNCARPETED">Uncarpeted</option>
              </select>
            </div>
          </div>
        </div>

        <div className={`mb-4 ${style.buttonContainer}`}>
          <button
            type="button"
            className={`${style.tabButton} ${activeTab === "listView" ? style.active : ''}`}
            onClick={() => switchTab("listView")}
            >
            List <img className={style.buttonIcons} src='/images/list_icon.png' alt='list icon'/>
          </button>
          <button
            type="button"
            className={`${style.tabButton} ${activeTab === "mapView" ? style.active : ''}`}
            onClick={() => switchTab("mapView")}
            >
            Map <img className={style.buttonIcons} src='/images/map_icon.png' alt='map icon'/>
          </button>
        </div>

        {statistics && statistics.length > 0 && adverts && adverts.length > 0 && (
          <div className={`row ${style.statisticsContainer}`}>
            <Carousel className='mt-3 mb-2' interval={3000}>
                      {statistics.map((statistic, index) => (
                        <Carousel.Item className="text-center mb-4" key={index}>
                          <p><b>{statistic}</b></p>
                        </Carousel.Item>
                      ))}
            </Carousel>
          </div>
        )}

        {activeTab === "listView" ? (
          <div>
            <div className='row'>
                <div className='col-sm-12 col-md-11'>
                  {adverts && adverts.length > 0 ? (
                    adverts.map((advert) => (
                      <div className='mt-5' key={advert.id}>
                        <Advert advert={advert} />
                      </div>
                    ))
                  ) : (
                    <div className='text-center mt-5'>
                      <h4>No adverts found in this city</h4>
                    </div>
                  )}
                </div>
            </div>
        </div>
        ) : (
        <div className='row'>
          <div className={`col-sm-12 col-md-11 mb-5`}>
              <AdvertsMap adverts={adverts} selectedCity={filteringCriteria.city}/>
          </div>
        </div>
        )}

        {totalPages > 0 && (
              <div className='row text-center mt-4'>
                <ReactPaginate 
                  breakLabel="..."
                  nextLabel=">"
                  onPageChange={handlePageChange}
                  pageRangeDisplayed={3}
                  pageCount={totalPages}
                  previousLabel="<"
                  renderOnZeroPageCount={null}
                  containerClassName={`${style.pagination}`}
                  pageLinkClassName={`${style.pageNum}`}
                  previousLinkClassName={`${style.pageNum}`}
                  nextLinkClassName={`${style.pageNum}`}
                  activeLinkClassName={`${style.active}`}
                />
              </div>
            )}    
      </div>
    );
    
}

export default AdvertsPage