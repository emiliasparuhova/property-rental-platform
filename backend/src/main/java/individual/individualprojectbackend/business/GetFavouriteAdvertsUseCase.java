package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.FavouriteAdvert;

import java.util.List;

public interface GetFavouriteAdvertsUseCase {
    List<FavouriteAdvert> getFavouriteAdverts(Long userId);
}
