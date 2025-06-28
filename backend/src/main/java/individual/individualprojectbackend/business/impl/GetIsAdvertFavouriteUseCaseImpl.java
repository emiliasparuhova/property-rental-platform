package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetIsAdvertFavouriteUseCase;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetIsAdvertFavouriteUseCaseImpl implements GetIsAdvertFavouriteUseCase {
    private FavouriteAdvertRepository favouriteAdvertRepository;
    @Override
    public boolean getIsAdvertFavourite(Long userId, Long advertId) {
        return favouriteAdvertRepository.existsByUser_IdAndAdvert_Id(userId, advertId);
    }
}
