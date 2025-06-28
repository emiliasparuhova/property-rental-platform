package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetAdvertUseCase;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class GetAdvertUseCaseImpl implements GetAdvertUseCase {
    private AdvertRepository advertRepository;


    @Override
    public Advert getAdvert(Long id) {
        Advert advert = advertRepository.findById(id)
                .map(AdvertConverter::convertToDomain)
                .orElse(null);


        if (Objects.isNull(advert)){
            return null;
        }

        return advert;
    }
}
