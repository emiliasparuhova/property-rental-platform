package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.ExternalAccount;

public interface GetGoogleUserUseCase {
    ExternalAccount getGoogleUser(String accessToken);
}
