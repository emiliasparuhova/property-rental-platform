package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateFavouriteAdvertUseCase;
import individual.individualprojectbackend.business.converter.FavouriteAdvertConverter;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateFavouriteAdvertUseCaseImpl implements CreateFavouriteAdvertUseCase {
    private FavouriteAdvertRepository favouriteAdvertRepository;
    @Override
    public Long createFavouriteAdvert(FavouriteAdvert favouriteAdvert) {
        favouriteAdvert.setTimestamp(LocalDateTime.now());
        return favouriteAdvertRepository.save(FavouriteAdvertConverter.convertToEntity(favouriteAdvert)).getId();
    }
}
