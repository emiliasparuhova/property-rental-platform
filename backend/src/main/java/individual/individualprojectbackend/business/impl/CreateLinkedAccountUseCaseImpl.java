package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateLinkedAccountUseCase;
import individual.individualprojectbackend.business.converter.LinkedAccountConverter;
import individual.individualprojectbackend.business.exception.ExistingLinkedAccountException;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateLinkedAccountUseCaseImpl implements CreateLinkedAccountUseCase {
    private LinkedAccountRepository linkedAccountRepository;

    @Override
    public Long createLinkedAccount(LinkedAccount linkedAccount) {
        if (linkedAccountRepository.existsByUser_IdAndProvider(linkedAccount.getUser().getId(),
                linkedAccount.getExternalAccount().getProvider()) ||
                linkedAccountRepository.existsByLinkedId(linkedAccount.getExternalAccount().getId())){
            throw new ExistingLinkedAccountException();
        }

        return linkedAccountRepository.save(LinkedAccountConverter.convertToEntity(linkedAccount)).getId();
    }
}
