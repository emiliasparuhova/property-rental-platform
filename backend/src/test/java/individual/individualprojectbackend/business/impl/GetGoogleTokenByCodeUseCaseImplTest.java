package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.external.OAuth2GoogleClient;
import individual.individualprojectbackend.external.dto.OAuth2GoogleTokenDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGoogleTokenByCodeUseCaseImplTest {

    @Mock
    OAuth2GoogleClient oAuth2GoogleClientMock;

    @InjectMocks
    GetGoogleTokenByCodeUseCaseImpl getGoogleTokenByCodeUseCase;


    @Test
    void getTokenByCode_returnsAccessTokenWhenCodeIsValid() {
        String validCode = "validAuthorizationCode";
        String expectedAccessToken = "validAccessToken";

        OAuth2GoogleTokenDTO oAuth2GoogleTokenDTO = OAuth2GoogleTokenDTO.builder()
                .accessToken(expectedAccessToken)
                .build();

        when(oAuth2GoogleClientMock.exchangeCodeForToken(validCode)).thenReturn(oAuth2GoogleTokenDTO);

        String actualAccessToken = getGoogleTokenByCodeUseCase.getTokenByCode(validCode);

        assertEquals(expectedAccessToken, actualAccessToken);
        verify(oAuth2GoogleClientMock, times(1)).exchangeCodeForToken(validCode);
    }


}