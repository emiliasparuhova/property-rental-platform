package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class CreatePropertyRequest {
    private Long id;
    @Min(value = 1, message = "The room size must be grater than 0")
    private int size;
    @Min(value = 1, message = "The number of rooms must be grater than 0")
    private int numberOfRooms;
    @NotNull(message = "The property type filed is mandatory")
    private PropertyType propertyType;
    @NotNull(message = "The furnishing type field is mandatory")
    private PropertyFurnishingType furnishingType;
    @Valid
    private CreateAddressRequest address;
}
