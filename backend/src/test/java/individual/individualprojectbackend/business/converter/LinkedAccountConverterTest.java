package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LinkedAccountConverterTest {

    @Test
    void convertToDomain_successful() {
        LinkedAccountEntity mockLinkedAccountEntity = mock(LinkedAccountEntity.class);
        when(mockLinkedAccountEntity.getId()).thenReturn(1L);
        when(mockLinkedAccountEntity.getLinkedId()).thenReturn("linkedId");
        when(mockLinkedAccountEntity.getLinkedEmail()).thenReturn("linked@example.com");
        when(mockLinkedAccountEntity.getLinkedName()).thenReturn("Linked User");
        when(mockLinkedAccountEntity.getProvider()).thenReturn("MockProvider");
        when(mockLinkedAccountEntity.getUser()).thenReturn(mock(UserEntity.class));

        LinkedAccount convertedLinkedAccount = LinkedAccountConverter.convertToDomain(mockLinkedAccountEntity);

        assertEquals(1L, convertedLinkedAccount.getId());
        assertEquals("linkedId", convertedLinkedAccount.getExternalAccount().getId());
        assertEquals("linked@example.com", convertedLinkedAccount.getExternalAccount().getEmail());
        assertEquals("Linked User", convertedLinkedAccount.getExternalAccount().getName());
        assertEquals("MockProvider", convertedLinkedAccount.getExternalAccount().getProvider());
    }

    @Test
    void convertToEntity_successful() {
        LinkedAccount mockLinkedAccount = mock(LinkedAccount.class);
        ExternalAccount mockExternalAccount = mock(ExternalAccount.class);
        when(mockLinkedAccount.getId()).thenReturn(1L);
        when(mockExternalAccount.getId()).thenReturn("linkedId");
        when(mockExternalAccount.getEmail()).thenReturn("linked@example.com");
        when(mockExternalAccount.getName()).thenReturn("Linked User");
        when(mockExternalAccount.getProvider()).thenReturn("MockProvider");
        when(mockLinkedAccount.getExternalAccount()).thenReturn(mockExternalAccount);
        when(mockLinkedAccount.getUser()).thenReturn(mock(User.class));

        LinkedAccountEntity convertedLinkedAccountEntity = LinkedAccountConverter.convertToEntity(mockLinkedAccount);

        assertEquals(1L, convertedLinkedAccountEntity.getId());
        assertEquals("linkedId", convertedLinkedAccountEntity.getLinkedId());
        assertEquals("linked@example.com", convertedLinkedAccountEntity.getLinkedEmail());
        assertEquals("Linked User", convertedLinkedAccountEntity.getLinkedName());
        assertEquals("MockProvider", convertedLinkedAccountEntity.getProvider());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        LinkedAccount convertedLinkedAccount = LinkedAccountConverter.convertToDomain(null);

        assertNull(convertedLinkedAccount.getId());
        assertNull(convertedLinkedAccount.getExternalAccount());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        LinkedAccountEntity convertedLinkedAccountEntity = LinkedAccountConverter.convertToEntity(null);

        assertNull(convertedLinkedAccountEntity.getId());
        assertNull(convertedLinkedAccountEntity.getLinkedId());
        assertNull(convertedLinkedAccountEntity.getLinkedEmail());
        assertNull(convertedLinkedAccountEntity.getLinkedName());
        assertNull(convertedLinkedAccountEntity.getProvider());
    }

}