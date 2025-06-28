package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.CityStatistics;

public interface GetCityStatisticsUseCase {
    CityStatistics getCityStatistics(String city);
}
