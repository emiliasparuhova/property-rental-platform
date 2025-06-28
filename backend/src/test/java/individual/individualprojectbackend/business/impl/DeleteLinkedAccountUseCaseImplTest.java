package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteLinkedAccountUseCaseImplTest {

    @Mock
    LinkedAccountRepository linkedAccountRepositoryMock;

    @InjectMocks
    DeleteLinkedAccountUseCaseImpl deleteLinkedAccountUseCase;


    @Test
    void deleteLinkedAccount_successfulWhenIdExists() {
        Long accountIdToDelete = 1L;

        deleteLinkedAccountUseCase.deleteLinkedAccount(accountIdToDelete);

        verify(linkedAccountRepositoryMock, times(1)).deleteById(accountIdToDelete);
    }

}