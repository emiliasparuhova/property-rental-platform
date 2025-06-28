package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.Objects;

public final class AdvertConverter {

    private AdvertConverter(){
    }

    public static Advert convertToDomain(AdvertEntity advertEntity){
        if (Objects.isNull(advertEntity)){
            return Advert.builder().build();
        }

        return Advert.builder()
                .id(advertEntity.getId())
                .price(advertEntity.getPrice())
                .description(advertEntity.getDescription())
                .property(PropertyConverter.convertToDomain(advertEntity.getProperty()))
                .creator(UserConverter.convertToDomain(advertEntity.getCreator()))
                .utilitiesIncluded(advertEntity.isUtilitiesIncluded())
                .availableFrom(advertEntity.getAvailableFrom())
                .creationDate(advertEntity.getCreationDate())
                .photos(advertEntity.getPhotos().stream().map(PhotoConverter::convertToByteArray).toList())
                .build();
    }

    public static AdvertEntity convertToEntity(Advert advert) {
        if (Objects.isNull(advert)) {
            return AdvertEntity.builder().build();
        }

        AdvertEntity advertEntity = AdvertEntity.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .description(advert.getDescription())
                .property(PropertyConverter.convertToEntity(advert.getProperty()))
                .creator(UserConverter.convertToEntity(advert.getCreator()))
                .utilitiesIncluded(advert.isUtilitiesIncluded())
                .availableFrom(advert.getAvailableFrom())
                .creationDate(advert.getCreationDate())
                .photos(new ArrayList<>())
                .build();

        if (advert.getPhotos() != null) {
            advert.getPhotos().forEach(photo -> {
                PhotoEntity photoEntity = PhotoConverter.convertToEntity(photo, advertEntity);
                photoEntity.setAdvert(advertEntity);
                advertEntity.getPhotos().add(photoEntity);
            });
        }

        return advertEntity;
    }


}
