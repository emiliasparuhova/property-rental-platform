package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.DeleteUserUseCase;
import individual.individualprojectbackend.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private UserRepository userRepository;
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
