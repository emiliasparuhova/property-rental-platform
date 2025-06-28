package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetCityStatisticsUseCase;
import individual.individualprojectbackend.domain.CityStatistics;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCityStatisticsUseCaseImpl implements GetCityStatisticsUseCase {

    private AdvertRepository advertRepository;
    @Override
    public CityStatistics getCityStatistics(String city) {
        int advertsCount = advertRepository.countAdvertsByCity(city).orElse(0);
        double averageAdvertPrice = advertRepository.calculateAveragePriceByCity(city).orElse(0.0);
        double averagePropertySize = advertRepository.calculateAveragePropertySizeByCity(city).orElse(0.0);

        return CityStatistics.builder()
                .advertsCount(advertsCount)
                .averageAdvertPrice(averageAdvertPrice)
                .averagePropertySize(averagePropertySize)
                .build();
    }
}
