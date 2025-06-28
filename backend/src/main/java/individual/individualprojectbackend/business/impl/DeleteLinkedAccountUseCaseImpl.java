package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.DeleteLinkedAccountUseCase;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteLinkedAccountUseCaseImpl implements DeleteLinkedAccountUseCase {
    private LinkedAccountRepository linkedAccountRepository;
    @Override
    public void deleteLinkedAccount(Long id) {
        linkedAccountRepository.deleteById(id);
    }
}
