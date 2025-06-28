package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PhotoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PhotoConverterTest {

    @Test
    void convertToByteArray_successful() {
        PhotoEntity mockPhotoEntity = mock(PhotoEntity.class);
        byte[] mockPhotoData = new byte[10];
        when(mockPhotoEntity.getPhotoData()).thenReturn(mockPhotoData);

        byte[] convertedByteArray = PhotoConverter.convertToByteArray(mockPhotoEntity);

        assertNotNull(convertedByteArray);
        assertArrayEquals(mockPhotoData, convertedByteArray);
    }

    @Test
    void convertToByteArray_handlesNullPhotoEntity() {
        byte[] convertedByteArray = PhotoConverter.convertToByteArray(null);

        assertArrayEquals(new byte[0], convertedByteArray);
    }

    @Test
    void convertToEntity_successful() {
        AdvertEntity mockAdvertEntity = mock(AdvertEntity.class);
        byte[] mockPhotoData = new byte[10];

        PhotoEntity convertedPhotoEntity = PhotoConverter.convertToEntity(mockPhotoData, mockAdvertEntity);

        assertNotNull(convertedPhotoEntity);
        assertArrayEquals(mockPhotoData, convertedPhotoEntity.getPhotoData());
    }

    @Test
    void convertToEntity_handlesNullPhotoData() {
        byte[] mockPhotoData = null;

        PhotoEntity convertedPhotoEntity = PhotoConverter.convertToEntity(mockPhotoData, null);

        assertNotNull(convertedPhotoEntity);
        assertArrayEquals(mockPhotoData, convertedPhotoEntity.getPhotoData());
    }

}