package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.AdvertFilterCriteria;

public interface GetAdvertsCountUseCase {

    int getAdvertsCount(AdvertFilterCriteria filterCriteria);
}
