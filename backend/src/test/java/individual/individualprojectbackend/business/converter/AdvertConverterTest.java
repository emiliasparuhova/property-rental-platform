package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.Property;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PropertyEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertConverterTest {

    @Test
    void convertToDomain_successful() {
        AdvertEntity mockAdvertEntity = mock(AdvertEntity.class);
        when(mockAdvertEntity.getId()).thenReturn(1L);
        when(mockAdvertEntity.getPrice()).thenReturn(1000.0);
        when(mockAdvertEntity.getDescription()).thenReturn("MockDescription");
        when(mockAdvertEntity.getProperty()).thenReturn(mock(PropertyEntity.class));
        when(mockAdvertEntity.getCreator()).thenReturn(mock(UserEntity.class));
        when(mockAdvertEntity.isUtilitiesIncluded()).thenReturn(true);
        when(mockAdvertEntity.getAvailableFrom()).thenReturn(LocalDate.now());
        when(mockAdvertEntity.getCreationDate()).thenReturn(LocalDate.now());
        when(mockAdvertEntity.getPhotos()).thenReturn(new ArrayList<>());

        Advert convertedAdvert = AdvertConverter.convertToDomain(mockAdvertEntity);

        assertEquals(1L, convertedAdvert.getId());
        assertEquals(1000.0, convertedAdvert.getPrice());
        assertEquals("MockDescription", convertedAdvert.getDescription());
        assertNotNull(convertedAdvert.getProperty());
        assertNotNull(convertedAdvert.getCreator());
        assertTrue(convertedAdvert.isUtilitiesIncluded());
        assertEquals(LocalDate.now(), convertedAdvert.getAvailableFrom());
        assertEquals(LocalDate.now(), convertedAdvert.getCreationDate());
        assertNotNull(convertedAdvert.getPhotos());
    }

    @Test
    void convertToEntity_successful() {
        Advert mockAdvert = mock(Advert.class);
        when(mockAdvert.getId()).thenReturn(1L);
        when(mockAdvert.getPrice()).thenReturn(1000.0);
        when(mockAdvert.getDescription()).thenReturn("MockDescription");
        when(mockAdvert.getProperty()).thenReturn(mock(Property.class));
        when(mockAdvert.getCreator()).thenReturn(mock(User.class));
        when(mockAdvert.isUtilitiesIncluded()).thenReturn(true);
        when(mockAdvert.getAvailableFrom()).thenReturn(LocalDate.now());
        when(mockAdvert.getCreationDate()).thenReturn(LocalDate.now());
        when(mockAdvert.getPhotos()).thenReturn(new ArrayList<>());

        AdvertEntity convertedAdvertEntity = AdvertConverter.convertToEntity(mockAdvert);

        assertEquals(1L, convertedAdvertEntity.getId());
        assertEquals(1000.0, convertedAdvertEntity.getPrice());
        assertEquals("MockDescription", convertedAdvertEntity.getDescription());
        assertNotNull(convertedAdvertEntity.getProperty());
        assertNotNull(convertedAdvertEntity.getCreator());
        assertTrue(convertedAdvertEntity.isUtilitiesIncluded());
        assertEquals(LocalDate.now(), convertedAdvertEntity.getAvailableFrom());
        assertEquals(LocalDate.now(), convertedAdvertEntity.getCreationDate());
        assertNotNull(convertedAdvertEntity.getPhotos());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        Advert convertedAdvert = AdvertConverter.convertToDomain(null);

        assertNull(convertedAdvert.getId());
        assertEquals(0.0, convertedAdvert.getPrice());
        assertNull(convertedAdvert.getDescription());
        assertNull(convertedAdvert.getProperty());
        assertNull(convertedAdvert.getCreator());
        assertFalse(convertedAdvert.isUtilitiesIncluded());
        assertNull(convertedAdvert.getAvailableFrom());
        assertNull(convertedAdvert.getCreationDate());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        AdvertEntity convertedAdvertEntity = AdvertConverter.convertToEntity(null);

        assertNotNull(convertedAdvertEntity);
        assertNull(convertedAdvertEntity.getId());
        assertEquals(0.0, convertedAdvertEntity.getPrice());
        assertNull(convertedAdvertEntity.getDescription());
        assertNull(convertedAdvertEntity.getProperty());
        assertNull(convertedAdvertEntity.getCreator());
        assertFalse(convertedAdvertEntity.isUtilitiesIncluded());
        assertNull(convertedAdvertEntity.getAvailableFrom());
        assertNull(convertedAdvertEntity.getCreationDate());
    }


    @Test
    void convertToEntity_handlesNullPhotos() {
        Advert mockAdvert = mock(Advert.class);
        when(mockAdvert.getPhotos()).thenReturn(null);

        AdvertEntity convertedAdvertEntity = AdvertConverter.convertToEntity(mockAdvert);

        assertNotNull(convertedAdvertEntity);
        assertNotNull(convertedAdvertEntity.getPhotos());
        assertTrue(convertedAdvertEntity.getPhotos().isEmpty());
    }
}