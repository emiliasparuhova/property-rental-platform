package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.FavouriteAdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import individual.individualprojectbackend.persistence.entity.FavouriteAdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFavouriteAdvertUseCaseImplTest {

    @Mock
    FavouriteAdvertRepository favouriteAdvertRepositoryMock;

    @InjectMocks
    CreateFavouriteAdvertUseCaseImpl createFavouriteAdvertUseCase;


    @Test
    void createFavouriteAdvert_successfulWhenInputIsValid() {
        FavouriteAdvert favouriteAdvert = FavouriteAdvert.builder()
                .user(User.builder().id(1L).build())
                .advert(Advert.builder().id(1L).build())
                .timestamp(LocalDateTime.now())
                .build();

        FavouriteAdvertEntity favouriteAdvertEntity = FavouriteAdvertConverter.convertToEntity(favouriteAdvert);
        favouriteAdvertEntity.setId(1L);

        when(favouriteAdvertRepositoryMock.save(any(FavouriteAdvertEntity.class))).thenReturn(favouriteAdvertEntity);

        Long actualResult = createFavouriteAdvertUseCase.createFavouriteAdvert(favouriteAdvert);
        Long expectedResult = 1L;

        assertEquals(expectedResult, actualResult);
        verify(favouriteAdvertRepositoryMock, times(1)).save(any(FavouriteAdvertEntity.class));
    }
}