package individual.individualprojectbackend.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOAuth2RequestUseCaseImplTest {

    @Mock
    ClientRegistrationRepository clientRegistrationRepositoryMock;

    @InjectMocks
    CreateOAuth2RequestUseCaseImpl createOAuth2RequestUseCase;

    @Test
    void createOAuth2Request_returnsOAuth2AuthorizationRequest_whenSuccessful() {
        String provider = "google";

        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId(provider)
                .clientId("clientId")
                .authorizationUri("authorizationUri")
                .redirectUri("redirectUri")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .tokenUri("tokenUri")
                .scope("scope")
                .build();

        when(clientRegistrationRepositoryMock.findByRegistrationId(provider)).thenReturn(clientRegistration);

        OAuth2AuthorizationRequest result = createOAuth2RequestUseCase.createOAuth2Request(provider);

        assertEquals(OAuth2AuthorizationResponseType.CODE, result.getResponseType());
        Map<String, Object> additionalParameters = result.getAdditionalParameters();
        assertNotNull(additionalParameters);
        assertEquals("consent", additionalParameters.get("prompt"));
        assertEquals("offline", additionalParameters.get("access_type"));
        assertEquals("true", additionalParameters.get("include_granted_scopes"));

        verify(clientRegistrationRepositoryMock, times(1)).findByRegistrationId(provider);
    }

}