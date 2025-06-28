package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import individual.individualprojectbackend.persistence.entity.FavouriteAdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetFavouriteAdvertsUseCaseImplTest {
    @Mock
    FavouriteAdvertRepository favouriteAdvertRepositoryMock;

    @InjectMocks
    GetFavouriteAdvertsUseCaseImpl getFavouriteAdvertsUseCase;


    @Test
    void getFavouriteAdverts_returnsListOfFavouriteAdverts() {
        Long userId = 1L;
        List<FavouriteAdvertEntity> favouriteAdvertEntities = Arrays.asList(
                FavouriteAdvertEntity.builder().id(1L).build(),
                FavouriteAdvertEntity.builder().id(2L).build()
        );

        when(favouriteAdvertRepositoryMock.findByUser_Id(userId)).thenReturn(favouriteAdvertEntities);

        List<FavouriteAdvert> actualResult = getFavouriteAdvertsUseCase.getFavouriteAdverts(userId);

        assertNotNull(actualResult);
        assertEquals(favouriteAdvertEntities.size(), actualResult.size());

        verify(favouriteAdvertRepositoryMock, times(1)).findByUser_Id(userId);
    }
}