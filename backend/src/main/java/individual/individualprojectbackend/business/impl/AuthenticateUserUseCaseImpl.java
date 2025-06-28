package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.AuthenticateUserUseCase;
import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.InvalidCredentialsException;
import individual.individualprojectbackend.configuration.security.token.AccessTokenEncoder;
import individual.individualprojectbackend.configuration.security.token.impl.AccessTokenImpl;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;

    private final PasswordHashingService passwordHashingService;

    public AuthenticateUserUseCaseImpl(UserRepository userRepository, AccessTokenEncoder accessTokenEncoder){
        this.userRepository = userRepository;
        this.accessTokenEncoder = accessTokenEncoder;
        this.passwordHashingService = new PasswordHashingService();
    }

    @Override
    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .map(UserConverter::convertToDomain)
                .orElse(null);

        if (Objects.isNull(user)){
            throw new InvalidCredentialsException();
        }

        if (!passwordHashingService.verifyPassword(password, user.getHashedPassword())){
            throw new InvalidCredentialsException();
        }

        return generateAccessToken(user);
    }

    private String generateAccessToken(User user) {
        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), user.getId(), user.getRole().name()));
    }
}
