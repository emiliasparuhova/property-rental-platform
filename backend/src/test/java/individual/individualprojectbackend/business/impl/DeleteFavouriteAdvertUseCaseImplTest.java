package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.persistence.FavouriteAdvertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteFavouriteAdvertUseCaseImplTest {

    @Mock
    FavouriteAdvertRepository favouriteAdvertRepositoryMock;

    @InjectMocks
    DeleteFavouriteAdvertUseCaseImpl deleteFavouriteAdvertUseCase;


    @Test
    void deleteFavouriteAdvert_successfulWhenInputIsValid() {
        Long userId = 1L;
        Long advertId = 2L;

        deleteFavouriteAdvertUseCase.deleteFavouriteAdvert(userId, advertId);

        verify(favouriteAdvertRepositoryMock, times(1)).deleteByUser_IdAndAdvert_Id(userId, advertId);
    }
}