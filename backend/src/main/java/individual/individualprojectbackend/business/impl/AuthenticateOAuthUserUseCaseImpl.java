package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.AuthenticateOAuthUserUseCase;
import individual.individualprojectbackend.business.converter.LinkedAccountConverter;
import individual.individualprojectbackend.business.exception.InvalidCredentialsException;
import individual.individualprojectbackend.configuration.security.token.AccessTokenEncoder;
import individual.individualprojectbackend.configuration.security.token.impl.AccessTokenImpl;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticateOAuthUserUseCaseImpl implements AuthenticateOAuthUserUseCase {

    private final LinkedAccountRepository linkedAccountRepository;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public String authenticateOAuthUser(String linkedAccountUserId) {
        Optional<LinkedAccountEntity> linkedOptional = linkedAccountRepository.findFirstByLinkedId(linkedAccountUserId);

        if (linkedOptional.isEmpty()){
            throw new InvalidCredentialsException();
        }

        LinkedAccount linkedAccount = LinkedAccountConverter.convertToDomain(linkedOptional.get());

        return generateAccessToken(linkedAccount.getUser());
    }

    private String generateAccessToken(User user) {
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), user.getId(), user.getRole().name()));
    }
}
