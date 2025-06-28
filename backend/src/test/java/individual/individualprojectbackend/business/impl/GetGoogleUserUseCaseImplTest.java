package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.ExternalAccountConverter;
import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.external.OAuth2GoogleClient;
import individual.individualprojectbackend.external.dto.OAuth2UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGoogleUserUseCaseImplTest {

    @Mock
    OAuth2GoogleClient oAuth2GoogleClientMock;

    @InjectMocks
    GetGoogleUserUseCaseImpl getGoogleUserUseCase;


    @Test
    void getGoogleUser_returnsExternalAccountWhenAccessTokenIsValid() {
        String validAccessToken = "validAccessToken";

        OAuth2UserDTO oAuth2UserDTO = OAuth2UserDTO.builder()
                .id("googleUserId")
                .email("test@example.com")
                .name("Test User")
                .provider("Google")
                .build();

        when(oAuth2GoogleClientMock.getUserProfile(validAccessToken)).thenReturn(oAuth2UserDTO);

        ExternalAccount actualExternalAccount = getGoogleUserUseCase.getGoogleUser(validAccessToken);

        ExternalAccount expectedExternalAccount = ExternalAccountConverter.convertToDomain(oAuth2UserDTO);
        assertEquals(expectedExternalAccount, actualExternalAccount);

        verify(oAuth2GoogleClientMock, times(1)).getUserProfile(validAccessToken);
    }

}