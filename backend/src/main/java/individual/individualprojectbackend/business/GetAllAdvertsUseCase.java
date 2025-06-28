package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;

import java.util.List;

public interface GetAllAdvertsUseCase {
    List<Advert> getAllAdverts(AdvertFilterCriteria filterCriteria);
}
