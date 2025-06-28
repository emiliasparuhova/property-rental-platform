package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Property;
import individual.individualprojectbackend.persistence.entity.PropertyEntity;

import java.util.Objects;

public final class PropertyConverter {

    private PropertyConverter() {
    }

    public static Property convertToDomain(PropertyEntity propertyEntity) {
        if (Objects.isNull(propertyEntity)) {
            return Property.builder().build();
        }

        return Property.builder()
                .id(propertyEntity.getId())
                .size(propertyEntity.getSize())
                .numberOfRooms(propertyEntity.getNumberOfRooms())
                .propertyType(propertyEntity.getPropertyType())
                .furnishingType(propertyEntity.getFurnishingType())
                .address(AddressConverter.convertToDomain(propertyEntity.getAddress()))
                .build();
    }

    public static PropertyEntity convertToEntity(Property property) {
        if (Objects.isNull(property)) {
            return PropertyEntity.builder().build();
        }

        return PropertyEntity.builder()
                .id(property.getId())
                .size(property.getSize())
                .numberOfRooms(property.getNumberOfRooms())
                .propertyType(property.getPropertyType())
                .furnishingType(property.getFurnishingType())
                .address(AddressConverter.convertToEntity(property.getAddress()))
                .build();
    }
}
