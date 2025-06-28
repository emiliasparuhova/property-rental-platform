package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.City;

public interface GetCitiesByCountryUseCase {

    City[] getCitiesByCountryCode(String countryCode);
}
