package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.City;
import individual.individualprojectbackend.external.CountriesClient;
import individual.individualprojectbackend.external.dto.CityDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class GetCitiesByCountryUseCaseImplTest {

    @Mock
    CountriesClient countriesClient;

    @InjectMocks
    GetCitiesByCountryUseCaseImpl getCitiesByCountryUseCase;

    @Test
    void getCitiesByCountryCode_returnsArrayOfCities_whenCountryCodeExists(){
        CityDTO[] cities = new CityDTO[2];

        cities[0] = (CityDTO.builder()
                .id(1L)
                .name("Eindhoven")
                .build());
        cities[1] = (CityDTO.builder()
                .id(2L)
                .name("Rotterdam")
                .build());

        when(countriesClient.getCitiesByCountryCode("NL")).thenReturn(cities);

        City[] actualCities = getCitiesByCountryUseCase.getCitiesByCountryCode("NL");

        assertNotNull(actualCities);
        assertEquals(2, actualCities.length);

        verify(countriesClient, times(1)).getCitiesByCountryCode("NL");
    }


}