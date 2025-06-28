package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.LinkedAccount;

import java.util.List;

public interface GetLinkedAccountsUseCase {
    List<LinkedAccount> getLinkedAccounts(Long userId);
}
