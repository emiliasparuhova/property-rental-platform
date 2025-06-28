package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.UpdateUserUseCase;
import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.business.exception.EmailAlreadyInUseException;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordHashingService passwordHashingService;

    public UpdateUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordHashingService = new PasswordHashingService();
    }

    @Override
    public void updateUser(User updatedUser) {
        User userToUpdate = userRepository.findById(updatedUser.getId())
                .map(UserConverter::convertToDomain)
                .orElse(null);

        if (Objects.nonNull(userToUpdate)){
            if (!Objects.equals(userToUpdate.getEmail(), updatedUser.getEmail()) &&
                    userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new EmailAlreadyInUseException();
            }


            updateFields(userToUpdate, updatedUser);
            userRepository.save(UserConverter.convertToEntity(userToUpdate));
        }
    }

    private void updateFields(User userToUpdate, User updatedFieldsUser){
        if (!isEmpty(updatedFieldsUser.getHashedPassword()))
        {
            String hashedPassword = passwordHashingService.hashPassword(updatedFieldsUser.getHashedPassword());
            userToUpdate.setHashedPassword(hashedPassword);
        }

        userToUpdate.setName(updatedFieldsUser.getName());
        userToUpdate.setEmail(updatedFieldsUser.getEmail());
        userToUpdate.setDescription(updatedFieldsUser.getDescription());
        userToUpdate.setGender(updatedFieldsUser.getGender());
        userToUpdate.setBirthDate(updatedFieldsUser.getBirthDate());
        userToUpdate.setProfilePicture(updatedFieldsUser.getProfilePicture());
        userToUpdate.setStatus(updatedFieldsUser.getStatus());

        if (updatedFieldsUser.getAddress() != null) {
            userToUpdate.getAddress().setCity(updatedFieldsUser.getAddress().getCity());
            userToUpdate.getAddress().setStreet(updatedFieldsUser.getAddress().getStreet());
            userToUpdate.getAddress().setZipcode(updatedFieldsUser.getAddress().getZipcode());
        }
    }
}
