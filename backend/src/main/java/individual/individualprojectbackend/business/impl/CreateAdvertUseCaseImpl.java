package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateAdvertUseCase;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CreateAdvertUseCaseImpl implements CreateAdvertUseCase {
    private final AdvertRepository advertRepository;
    @Override
    public Long createAdvert(Advert advert) {
        advert.setCreationDate(LocalDate.now());
        return advertRepository.save(AdvertConverter.convertToEntity(advert)).getId();
    }
}
