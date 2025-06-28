package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetAllAdvertsRequest {
    private String city;
    private double minPrice;
    private double maxPrice;
    private PropertyType propertyType;
    private PropertyFurnishingType furnishingType;
    private int pageNumber;
    private int pageSize;
}
