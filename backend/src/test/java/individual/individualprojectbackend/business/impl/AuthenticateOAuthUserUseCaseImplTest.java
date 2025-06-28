package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.exception.InvalidCredentialsException;
import individual.individualprojectbackend.configuration.security.token.AccessTokenEncoder;
import individual.individualprojectbackend.configuration.security.token.impl.AccessTokenImpl;
import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateOAuthUserUseCaseImplTest {

    @Mock
    LinkedAccountRepository linkedAccountRepositoryMock;

    @Mock
    private AccessTokenEncoder accessTokenEncoderMock;

    @InjectMocks
    AuthenticateOAuthUserUseCaseImpl authenticateOAuthUserUseCase;

    @Test
    void authenticateOAuthUser_successfulWhenLinkedAccountExists() {
        String linkedAccountUserId = "someLinkedUserId";

        LinkedAccountEntity linkedAccountEntity = LinkedAccountEntity.builder()
                .id(1L)
                .user(UserEntity.builder()
                        .id(1L)
                        .role(UserRole.TENANT)
                        .email("email")
                        .build())
                .linkedId(linkedAccountUserId)
                .linkedEmail("test@example.com")
                .linkedName("Test User")
                .provider("Google")
                .build();

        when(linkedAccountRepositoryMock.findFirstByLinkedId(linkedAccountUserId)).thenReturn(Optional.of(linkedAccountEntity));
        when(accessTokenEncoderMock.encode(any(AccessTokenImpl.class))).thenReturn("encodedAccessToken");

        String actualResult = authenticateOAuthUserUseCase.authenticateOAuthUser(linkedAccountUserId);

        assertEquals("encodedAccessToken", actualResult);
        verify(linkedAccountRepositoryMock, times(1)).findFirstByLinkedId(linkedAccountUserId);
        verify(accessTokenEncoderMock, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    void authenticateOAuthUser_throwsInvalidCredentialsExceptionWhenLinkedAccountNotFound() {
        String linkedAccountUserId = "nonExistentLinkedUserId";

        when(linkedAccountRepositoryMock.findFirstByLinkedId(linkedAccountUserId)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> authenticateOAuthUserUseCase.authenticateOAuthUser(linkedAccountUserId));

        verify(linkedAccountRepositoryMock, times(1)).findFirstByLinkedId(linkedAccountUserId);
        verifyNoInteractions(accessTokenEncoderMock);
    }

}