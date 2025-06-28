package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.configuration.security.token.AccessToken;
import individual.individualprojectbackend.configuration.security.token.AccessTokenDecoder;
import individual.individualprojectbackend.configuration.security.token.impl.AccessTokenImpl;
import individual.individualprojectbackend.domain.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserIdFromTokenUseCaseImplTest {
    @Mock
    private AccessTokenDecoder decoderMock;

    @InjectMocks
    private GetUserIdFromTokenUseCaseImpl getUserIdFromTokenUseCase;

    @Test
    void getUserIdFromAccessToken_successful() {
        String accessToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String tokenToDecode = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        AccessToken decodedAccessToken = new AccessTokenImpl("email", 1L, UserRole.TENANT.name());

        when(decoderMock.decode(tokenToDecode)).thenReturn(decodedAccessToken);

        Long userId = getUserIdFromTokenUseCase.getIdFromToken(accessToken);

        assertEquals(1L, userId);
        verify(decoderMock, times(1)).decode(tokenToDecode);
    }


}