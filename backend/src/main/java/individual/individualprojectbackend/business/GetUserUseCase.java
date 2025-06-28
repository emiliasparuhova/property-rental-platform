package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.User;

public interface GetUserUseCase {
    User getUser(Long id);
}
