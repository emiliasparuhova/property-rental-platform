package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetLinkedAccountsUseCase;
import individual.individualprojectbackend.business.converter.LinkedAccountConverter;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetLinkedAccountsUseCaseImpl implements GetLinkedAccountsUseCase {
    private LinkedAccountRepository linkedAccountRepository;
    @Override
    public List<LinkedAccount> getLinkedAccounts(Long userId) {
        return linkedAccountRepository.findByUser_Id(userId).stream()
                .map(LinkedAccountConverter::convertToDomain)
                .toList();
    }
}
