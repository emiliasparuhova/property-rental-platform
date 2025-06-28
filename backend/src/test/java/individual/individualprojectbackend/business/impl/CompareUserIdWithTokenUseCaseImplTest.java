package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetUserIdFromTokenUseCase;
import individual.individualprojectbackend.business.exception.UserIdMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompareUserIdWithTokenUseCaseImplTest {
    @Mock
    private GetUserIdFromTokenUseCase getUserIdFromTokenUseCaseMock;

    @InjectMocks
    private CompareUserIdWithTokenUseCaseImpl compareUserIdWithTokenUseCase;

    @Test
    void compareUserIdWithTokenId_whenIdsMatch() {
        Long userId = 123L;
        String accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        when(getUserIdFromTokenUseCaseMock.getIdFromToken(accessToken)).thenReturn(123L);

        compareUserIdWithTokenUseCase.compareIdWithToken(userId, accessToken);

        verify(getUserIdFromTokenUseCaseMock, times(1)).getIdFromToken(accessToken);
    }

    @Test
    void compareUserIdWithTokenId_throwsUserIdMismatchException_whenIdsDontMatch() {
        Long userId = 123L;
        String accessToken = "Bearer someInvalidToken";

        when(getUserIdFromTokenUseCaseMock.getIdFromToken(accessToken)).thenReturn(456L);

        assertThrows(UserIdMismatchException.class,
                () -> compareUserIdWithTokenUseCase.compareIdWithToken(userId, accessToken));

        verify(getUserIdFromTokenUseCaseMock, times(1)).getIdFromToken(accessToken);
    }

}