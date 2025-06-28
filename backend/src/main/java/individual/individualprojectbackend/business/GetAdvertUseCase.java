package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Advert;

public interface GetAdvertUseCase {
    Advert getAdvert(Long id);
}
