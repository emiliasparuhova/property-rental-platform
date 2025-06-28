package individual.individualprojectbackend.domain;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdvertFilterCriteria {
    private String city;
    private double minPrice;
    private double maxPrice;
    private PropertyType propertyType;
    private PropertyFurnishingType furnishingType;
    private int pageNumber;
    private int pageSize;
}
