package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreatePropertyRequest;
import individual.individualprojectbackend.domain.Property;

public final class PropertyRequestsConverter {

    private PropertyRequestsConverter(){

    }

    public static Property convertCreateRequest(CreatePropertyRequest request){
        return Property.builder()
                .size(request.getSize())
                .numberOfRooms(request.getNumberOfRooms())
                .propertyType(request.getPropertyType())
                .furnishingType(request.getFurnishingType())
                .address(AddressRequestsConverter.convertCreateRequest(request.getAddress()))
                .build();
    }
}
