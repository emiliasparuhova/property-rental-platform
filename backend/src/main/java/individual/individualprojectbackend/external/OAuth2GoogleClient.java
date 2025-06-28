package individual.individualprojectbackend.external;

import individual.individualprojectbackend.external.dto.OAuth2GoogleTokenDTO;
import individual.individualprojectbackend.external.dto.OAuth2UserDTO;

public interface OAuth2GoogleClient {
    OAuth2GoogleTokenDTO exchangeCodeForToken(String code);
    OAuth2UserDTO getUserProfile(String accessToken);

}
