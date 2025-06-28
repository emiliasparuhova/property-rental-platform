package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateUserUseCase;
import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.EmailAlreadyInUseException;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHashingService passwordHashingService;

    public CreateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordHashingService = new PasswordHashingService();
    }

    @Override
    public Long createUser(User user, String plainTextPassword) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyInUseException();
        }

        String hashedPassword = passwordHashingService.hashPassword(plainTextPassword);
        user.setHashedPassword(hashedPassword);

        return userRepository.save(UserConverter.convertToEntity(user)).getId();
    }
}
