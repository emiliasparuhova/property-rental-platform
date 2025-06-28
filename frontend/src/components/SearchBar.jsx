import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import style from './SearchBar.module.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function SearchBar(props) {
  const [selectedCity, setSelectedCity] = useState(null);


  useEffect(() => {
    if (props.city) {
      setSelectedCity(props.city)
    }
  }, [props.city])

  const cities = props.cities;

  const handleChange = (selectedOption) => {
    if (!selectedOption) {
      setSelectedCity(null)
      props.handleCityChange('');
    } else {
      setSelectedCity(selectedOption);
      props.handleCityChange(selectedOption);
    }
  };

    return (
      <div className={`container ${style.searchBarContainer}`}>
        <div className="row">
          <div className="col">
            <Select
              className={`${style.select}`}
              options={cities.map(city => ({ value: city.id, label: city.name }))}
              value={selectedCity}
              onChange={handleChange}
              isSearchable={true}
              isClearable={true}
              placeholder="City"
            />
          </div>

        </div>
      </div>
    );
}

export default SearchBar;
