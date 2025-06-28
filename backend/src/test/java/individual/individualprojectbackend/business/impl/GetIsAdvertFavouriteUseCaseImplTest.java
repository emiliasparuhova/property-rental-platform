package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetIsAdvertFavouriteUseCaseImplTest {

    @Mock
    FavouriteAdvertRepository favouriteAdvertRepositoryMock;

    @InjectMocks
    GetIsAdvertFavouriteUseCaseImpl getIsAdvertFavouriteUseCase;


    @Test
    void getIsAdvertFavourite_returnsTrueWhenAdvertIsFavourite() {
        Long userId = 1L;
        Long advertId = 2L;

        when(favouriteAdvertRepositoryMock.existsByUser_IdAndAdvert_Id(userId, advertId)).thenReturn(true);

        boolean isAdvertFavourite = getIsAdvertFavouriteUseCase.getIsAdvertFavourite(userId, advertId);

        assertTrue(isAdvertFavourite);
        verify(favouriteAdvertRepositoryMock, times(1)).existsByUser_IdAndAdvert_Id(userId, advertId);
    }
}