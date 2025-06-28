package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PhotoEntity;

import java.util.Objects;

public final class PhotoConverter {

    private PhotoConverter() {
    }

    public static byte[] convertToByteArray(PhotoEntity photoEntity) {
        if (Objects.isNull(photoEntity)){
            return new byte[0];
        }

        return photoEntity.getPhotoData();
    }

    public static PhotoEntity convertToEntity(byte[] photoData, AdvertEntity advertEntity) {
        if (Objects.isNull(photoData)) {
            return PhotoEntity.builder().build();
        }

        return PhotoEntity.builder()
                .photoData(photoData)
                .advert(advertEntity)
                .build();
    }

}
