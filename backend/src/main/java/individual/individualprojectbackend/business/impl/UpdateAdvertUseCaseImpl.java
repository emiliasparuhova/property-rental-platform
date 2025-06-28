package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.UpdateAdvertUseCase;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UpdateAdvertUseCaseImpl implements UpdateAdvertUseCase{
    private AdvertRepository advertRepository;

    @Override
    public void updateAdvert(Advert updatedAdvert) {
        Advert advertToUpdate = advertRepository.findById(updatedAdvert.getId())
                .map(AdvertConverter::convertToDomain)
                .orElse(null);

        if (Objects.nonNull(advertToUpdate)){
            updateFields(advertToUpdate, updatedAdvert);
            advertRepository.save(AdvertConverter.convertToEntity(advertToUpdate));
        }
    }

    private void updateFields(Advert advertToUpdate, Advert updatedFieldsAdvert){
        advertToUpdate.setPrice(updatedFieldsAdvert.getPrice());
        advertToUpdate.setDescription(updatedFieldsAdvert.getDescription());
        advertToUpdate.setProperty(updatedFieldsAdvert.getProperty());
        advertToUpdate.setUtilitiesIncluded(updatedFieldsAdvert.isUtilitiesIncluded());
        advertToUpdate.setAvailableFrom(updatedFieldsAdvert.getAvailableFrom());
        advertToUpdate.setPhotos(updatedFieldsAdvert.getPhotos());
    }

}
