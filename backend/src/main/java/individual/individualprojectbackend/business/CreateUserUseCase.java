package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.User;

public interface CreateUserUseCase {
    Long createUser(User user, String plainTextPassword);
}
