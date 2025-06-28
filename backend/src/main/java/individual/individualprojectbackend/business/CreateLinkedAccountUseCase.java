package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.LinkedAccount;

public interface CreateLinkedAccountUseCase {
    Long createLinkedAccount(LinkedAccount linkedAccount);
}
