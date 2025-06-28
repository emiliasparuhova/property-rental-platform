package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLinkedAccountsUseCaseImplTest {

    @Mock
    LinkedAccountRepository linkedAccountRepositoryMock;

    @InjectMocks
    GetLinkedAccountsUseCaseImpl getLinkedAccountsUseCase;


    @Test
    void getLinkedAccounts_returnsListOfLinkedAccountsWhenUserIdExists() {
        Long userId = 1L;

        LinkedAccountEntity linkedAccountEntity1 = LinkedAccountEntity.builder().id(1L).build();
        LinkedAccountEntity linkedAccountEntity2 = LinkedAccountEntity.builder().id(2L).build();

        List<LinkedAccountEntity> linkedAccountEntities = List.of(linkedAccountEntity1, linkedAccountEntity2);

        when(linkedAccountRepositoryMock.findByUser_Id(userId)).thenReturn(linkedAccountEntities);

        List<LinkedAccount> actualResult = getLinkedAccountsUseCase.getLinkedAccounts(userId);

        assertEquals(2, actualResult.size());
        verify(linkedAccountRepositoryMock, times(1)).findByUser_Id(userId);
    }

    @Test
    void getLinkedAccounts_returnsEmptyListWhenUserIdDoesNotExist() {
        Long nonExistentUserId = 99L;

        when(linkedAccountRepositoryMock.findByUser_Id(nonExistentUserId)).thenReturn(List.of());

        List<LinkedAccount> actualResult = getLinkedAccountsUseCase.getLinkedAccounts(nonExistentUserId);

        assertEquals(0, actualResult.size());
        verify(linkedAccountRepositoryMock, times(1)).findByUser_Id(nonExistentUserId);
    }

}