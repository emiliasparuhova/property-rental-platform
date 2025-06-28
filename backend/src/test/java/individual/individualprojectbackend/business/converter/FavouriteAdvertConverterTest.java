package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.FavouriteAdvertEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FavouriteAdvertConverterTest {


    @Test
    void convertToDomain_successful() {
        FavouriteAdvertEntity mockFavouriteAdvertEntity = mock(FavouriteAdvertEntity.class);
        when(mockFavouriteAdvertEntity.getUser()).thenReturn(UserEntity.builder().id(1L).build());
        when(mockFavouriteAdvertEntity.getAdvert()).thenReturn(AdvertEntity.builder().id(2L).photos(new ArrayList<>()).build());
        when(mockFavouriteAdvertEntity.getTimestamp()).thenReturn(LocalDateTime.now());

        FavouriteAdvert convertedFavouriteAdvert = FavouriteAdvertConverter.convertToDomain(mockFavouriteAdvertEntity);

        assertNotNull(convertedFavouriteAdvert);
        assertNotNull(convertedFavouriteAdvert.getUser());
        assertNotNull(convertedFavouriteAdvert.getAdvert());
        assertNotNull(convertedFavouriteAdvert.getTimestamp());
    }

    @Test
    void convertToEntity_successful() {
        FavouriteAdvert mockFavouriteAdvert = mock(FavouriteAdvert.class);
        when(mockFavouriteAdvert.getUser()).thenReturn(User.builder().id(1L).build());
        when(mockFavouriteAdvert.getAdvert()).thenReturn(Advert.builder().id(2L).build());
        when(mockFavouriteAdvert.getTimestamp()).thenReturn(LocalDateTime.now());

        FavouriteAdvertEntity convertedFavouriteAdvertEntity = FavouriteAdvertConverter.convertToEntity(mockFavouriteAdvert);

        assertNotNull(convertedFavouriteAdvertEntity);
        assertNotNull(convertedFavouriteAdvertEntity.getUser());
        assertNotNull(convertedFavouriteAdvertEntity.getAdvert());
        assertNotNull(convertedFavouriteAdvertEntity.getTimestamp());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        FavouriteAdvert convertedFavouriteAdvert = FavouriteAdvertConverter.convertToDomain(null);

        assertNotNull(convertedFavouriteAdvert);
        assertNull(convertedFavouriteAdvert.getUser());
        assertNull(convertedFavouriteAdvert.getAdvert());
        assertNull(convertedFavouriteAdvert.getTimestamp());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        FavouriteAdvertEntity convertedFavouriteAdvertEntity = FavouriteAdvertConverter.convertToEntity(null);

        assertNotNull(convertedFavouriteAdvertEntity);
        assertNull(convertedFavouriteAdvertEntity.getUser());
        assertNull(convertedFavouriteAdvertEntity.getAdvert());
        assertNull(convertedFavouriteAdvertEntity.getTimestamp());
    }
}