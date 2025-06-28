package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetGoogleUserUseCase;
import individual.individualprojectbackend.business.converter.ExternalAccountConverter;
import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.external.OAuth2GoogleClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetGoogleUserUseCaseImpl implements GetGoogleUserUseCase {
    private OAuth2GoogleClient oAuth2GoogleClient;
    @Override
    public ExternalAccount getGoogleUser(String accessToken) {
        return ExternalAccountConverter.convertToDomain(oAuth2GoogleClient.getUserProfile(accessToken));
    }
}
