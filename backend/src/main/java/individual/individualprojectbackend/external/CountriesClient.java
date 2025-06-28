package individual.individualprojectbackend.external;

import individual.individualprojectbackend.external.dto.CityDTO;

public interface CountriesClient {

    CityDTO[] getCitiesByCountryCode(String countryCode);
}
