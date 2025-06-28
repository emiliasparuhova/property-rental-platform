package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.persistence.entity.AddressEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressConverterTest {

    @Test
    void convertToDomain_successful() {
        AddressEntity mockAddressEntity = mock(AddressEntity.class);
        when(mockAddressEntity.getId()).thenReturn(1L);
        when(mockAddressEntity.getCity()).thenReturn("MockCity");
        when(mockAddressEntity.getStreet()).thenReturn("MockStreet");
        when(mockAddressEntity.getZipcode()).thenReturn("MockZipcode");

        Address convertedAddress = AddressConverter.convertToDomain(mockAddressEntity);

        assertEquals(1L, convertedAddress.getId());
        assertEquals("MockCity", convertedAddress.getCity());
        assertEquals("MockStreet", convertedAddress.getStreet());
        assertEquals("MockZipcode", convertedAddress.getZipcode());
    }

    @Test
    void convertToEntity_successful() {
        Address mockAddress = mock(Address.class);
        when(mockAddress.getId()).thenReturn(1L);
        when(mockAddress.getCity()).thenReturn("MockCity");
        when(mockAddress.getStreet()).thenReturn("MockStreet");
        when(mockAddress.getZipcode()).thenReturn("MockZipcode");

        AddressEntity convertedAddressEntity = AddressConverter.convertToEntity(mockAddress);

        assertEquals(1L, convertedAddressEntity.getId());
        assertEquals("MockCity", convertedAddressEntity.getCity());
        assertEquals("MockStreet", convertedAddressEntity.getStreet());
        assertEquals("MockZipcode", convertedAddressEntity.getZipcode());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        Address convertedAddress = AddressConverter.convertToDomain(null);

        assertNull(convertedAddress.getId());
        assertNull(convertedAddress.getCity());
        assertNull(convertedAddress.getStreet());
        assertNull(convertedAddress.getZipcode());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        AddressEntity convertedAddressEntity = AddressConverter.convertToEntity(null);

        assertNull(convertedAddressEntity.getId());
        assertNull(convertedAddressEntity.getCity());
        assertNull(convertedAddressEntity.getStreet());
        assertNull(convertedAddressEntity.getZipcode());
    }

}