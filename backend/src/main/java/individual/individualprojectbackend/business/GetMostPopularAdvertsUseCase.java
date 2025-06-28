package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Advert;

import java.util.List;

public interface GetMostPopularAdvertsUseCase {

    List<Advert> getMostPopularAdverts(int advertCount);

}
