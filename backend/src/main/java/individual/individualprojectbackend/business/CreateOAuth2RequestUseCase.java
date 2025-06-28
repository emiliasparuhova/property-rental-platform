package individual.individualprojectbackend.business;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public interface CreateOAuth2RequestUseCase {

    OAuth2AuthorizationRequest createOAuth2Request(String provider);
}
