package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetGoogleTokenByCodeUseCase;
import individual.individualprojectbackend.external.OAuth2GoogleClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetGoogleTokenByCodeUseCaseImpl implements GetGoogleTokenByCodeUseCase {
    private OAuth2GoogleClient oAuth2GoogleClient;
    @Override
    public String getTokenByCode(String code) {
        return oAuth2GoogleClient.exchangeCodeForToken(code).getAccessToken();
    }
}
