package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetFavouriteAdvertsUseCase;
import individual.individualprojectbackend.business.converter.FavouriteAdvertConverter;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetFavouriteAdvertsUseCaseImpl implements GetFavouriteAdvertsUseCase {
    private FavouriteAdvertRepository favouriteAdvertRepository;
    @Override
    public List<FavouriteAdvert> getFavouriteAdverts(Long userId) {
        return favouriteAdvertRepository.findByUser_Id(userId).stream()
                .map(FavouriteAdvertConverter::convertToDomain)
                .toList();
    }
}
