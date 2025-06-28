package individual.individualprojectbackend.domain;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Property {
    private Long id;
    private int size;
    private int numberOfRooms;
    private PropertyType propertyType;
    private PropertyFurnishingType furnishingType;
    private Address address;
}
