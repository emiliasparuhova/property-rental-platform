package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateOAuth2RequestUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest.authorizationCode;

@Service
@AllArgsConstructor
public class CreateOAuth2RequestUseCaseImpl implements CreateOAuth2RequestUseCase {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Override
    public OAuth2AuthorizationRequest createOAuth2Request(String provider) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider);

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("prompt", "consent");
        additionalParameters.put("access_type", "offline");
        additionalParameters.put("response_type", "code");
        additionalParameters.put("include_granted_scopes", "true");
        additionalParameters.put("state", generateState());

        return authorizationCode()
                .clientId(clientRegistration.getClientId())
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(clientRegistration.getRedirectUri())
                .scope(String.join(" ", clientRegistration.getScopes()))
                .additionalParameters(additionalParameters)
                .build();
    }

    private String generateState() {
        return UUID.randomUUID().toString();
    }
}
