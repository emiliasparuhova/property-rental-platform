package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.CityStatistics;
import individual.individualprojectbackend.persistence.AdvertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCityStatisticsUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    GetCityStatisticsUseCaseImpl getCityStatisticsUseCase;


    @Test
    void getCityStatistics_successful() {
        String city = "TestCity";
        int advertsCount = 10;
        double averageAdvertPrice = 1000.0;
        double averagePropertySize = 150.0;

        when(advertRepositoryMock.countAdvertsByCity(city)).thenReturn(Optional.of(advertsCount));
        when(advertRepositoryMock.calculateAveragePriceByCity(city)).thenReturn(Optional.of(averageAdvertPrice));
        when(advertRepositoryMock.calculateAveragePropertySizeByCity(city)).thenReturn(Optional.of(averagePropertySize));

        CityStatistics result = getCityStatisticsUseCase.getCityStatistics(city);

        assertEquals(advertsCount, result.getAdvertsCount());
        assertEquals(averageAdvertPrice, result.getAverageAdvertPrice());
        assertEquals(averagePropertySize, result.getAveragePropertySize());

        verify(advertRepositoryMock).countAdvertsByCity(city);
        verify(advertRepositoryMock).calculateAveragePriceByCity(city);
        verify(advertRepositoryMock).calculateAveragePropertySizeByCity(city);
    }

}