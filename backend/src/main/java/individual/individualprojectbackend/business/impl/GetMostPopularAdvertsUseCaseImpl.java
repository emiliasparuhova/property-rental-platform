package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetMostPopularAdvertsUseCase;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetMostPopularAdvertsUseCaseImpl implements GetMostPopularAdvertsUseCase {
    private AdvertRepository advertRepository;
    @Override
    public List<Advert> getMostPopularAdverts(int advertCount) {
        return advertRepository.getMostPopularAdverts(advertCount).stream()
                .map(AdvertConverter::convertToDomain)
                .toList();
    }
}
