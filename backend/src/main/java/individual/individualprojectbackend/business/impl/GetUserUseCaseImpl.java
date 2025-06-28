package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetUserUseCase;
import individual.individualprojectbackend.business.converter.UserConverter;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;
    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id)
                .map(UserConverter::convertToDomain)
                .orElse(null);

        if (Objects.isNull(user)){
            return null;
        }

        return user;
    }
}
