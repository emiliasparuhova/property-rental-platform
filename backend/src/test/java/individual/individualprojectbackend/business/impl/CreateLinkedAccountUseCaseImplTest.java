package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.LinkedAccountConverter;
import individual.individualprojectbackend.business.exception.ExistingLinkedAccountException;
import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.LinkedAccountRepository;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateLinkedAccountUseCaseImplTest {

    @Mock
    LinkedAccountRepository linkedAccountRepositoryMock;

    @InjectMocks
    CreateLinkedAccountUseCaseImpl createLinkedAccountUseCase;

    @Test
    void createLinkedAccount_successfulWhenInputIsValid() {
        LinkedAccount linkedAccount = LinkedAccount.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .externalAccount(ExternalAccount.builder().id("someExternalAccountId").provider("Google").build())
                .build();

        LinkedAccountEntity linkedAccountEntity = LinkedAccountConverter.convertToEntity(linkedAccount);

        when(linkedAccountRepositoryMock.existsByUser_IdAndProvider(1L, "Google")).thenReturn(false);
        when(linkedAccountRepositoryMock.existsByLinkedId("someExternalAccountId")).thenReturn(false);
        when(linkedAccountRepositoryMock.save(linkedAccountEntity)).thenReturn(linkedAccountEntity);

        Long actualResult = createLinkedAccountUseCase.createLinkedAccount(linkedAccount);

        assertEquals(1L, actualResult);
        verify(linkedAccountRepositoryMock, times(1)).existsByUser_IdAndProvider(1L, "Google");
        verify(linkedAccountRepositoryMock, times(1)).existsByLinkedId("someExternalAccountId");
        verify(linkedAccountRepositoryMock, times(1)).save(linkedAccountEntity);
    }

    @Test
    void createLinkedAccount_throwsExistingLinkedAccountExceptionWhenLinkedAccountAlreadyExists() {
        LinkedAccount linkedAccount = LinkedAccount.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .externalAccount(ExternalAccount.builder().id("existingExternalAccountId").provider("Google").build())
                .build();

        when(linkedAccountRepositoryMock.existsByUser_IdAndProvider(1L, "Google")).thenReturn(true);

        assertThrows(ExistingLinkedAccountException.class, () -> createLinkedAccountUseCase.createLinkedAccount(linkedAccount));

        verify(linkedAccountRepositoryMock, times(1)).existsByUser_IdAndProvider(1L, "Google");
    }

    @Test
    void createLinkedAccount_throwsExistingLinkedAccountExceptionWhenLinkedAccountIsTaken() {
        LinkedAccount linkedAccount = LinkedAccount.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .externalAccount(ExternalAccount.builder().id("existingExternalAccountId").provider("Google").build())
                .build();

        when(linkedAccountRepositoryMock.existsByLinkedId("existingExternalAccountId")).thenReturn(true);

        assertThrows(ExistingLinkedAccountException.class, () -> createLinkedAccountUseCase.createLinkedAccount(linkedAccount));

        verify(linkedAccountRepositoryMock, times(1)).existsByLinkedId("existingExternalAccountId");
    }

}