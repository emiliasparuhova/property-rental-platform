package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetCitiesByCountryUseCase;
import individual.individualprojectbackend.business.converter.CityConverter;
import individual.individualprojectbackend.domain.City;
import individual.individualprojectbackend.external.CountriesClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class GetCitiesByCountryUseCaseImpl implements GetCitiesByCountryUseCase {
    private CountriesClient countriesClient;
    @Override
    public City[] getCitiesByCountryCode(String countryCode) {
        return Arrays.stream(countriesClient.getCitiesByCountryCode(countryCode))
                .map(CityConverter::convertToDomain)
                .toArray(City[]::new);
    }
}
