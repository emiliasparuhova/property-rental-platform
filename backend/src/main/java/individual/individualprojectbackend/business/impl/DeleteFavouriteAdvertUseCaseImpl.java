package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.DeleteFavouriteAdvertUseCase;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteFavouriteAdvertUseCaseImpl implements DeleteFavouriteAdvertUseCase {
    private FavouriteAdvertRepository favouriteAdvertRepository;
    @Override
    public void deleteFavouriteAdvert(Long userId, Long advertId) {
        favouriteAdvertRepository.deleteByUser_IdAndAdvert_Id(userId, advertId);
    }
}
