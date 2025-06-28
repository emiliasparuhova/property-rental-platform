package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateAddressRequest;
import individual.individualprojectbackend.domain.Address;

public final class AddressRequestsConverter {

    private AddressRequestsConverter(){}

    public static Address convertCreateRequest(CreateAddressRequest request){
        return Address.builder()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();
    }
}
