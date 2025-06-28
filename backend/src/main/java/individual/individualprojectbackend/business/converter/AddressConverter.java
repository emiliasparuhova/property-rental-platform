package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.persistence.entity.AddressEntity;

import java.util.Objects;

public final class AddressConverter {

    private AddressConverter() {
    }

    public static Address convertToDomain(AddressEntity addressEntity) {
        if (Objects.isNull(addressEntity)) {
            return Address.builder().build();
        }

        return Address.builder()
                .id(addressEntity.getId())
                .city(addressEntity.getCity())
                .street(addressEntity.getStreet())
                .zipcode(addressEntity.getZipcode())
                .build();
    }

    public static AddressEntity convertToEntity(Address address) {
        if (Objects.isNull(address)) {
            return AddressEntity.builder().build();
        }

        return AddressEntity.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .build();
    }
}
