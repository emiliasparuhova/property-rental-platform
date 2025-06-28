package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetCoordinatesByAddress;
import individual.individualprojectbackend.business.converter.CoordinatesConverter;
import individual.individualprojectbackend.domain.Coordinates;
import individual.individualprojectbackend.external.GeocodingClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCoordinatesByAddressImpl implements GetCoordinatesByAddress {

    private GeocodingClient geocodingClient;
    @Override
    public Coordinates getCoordinates(String address) {
        return CoordinatesConverter.convertToDomain(geocodingClient.getCoordinates(address));
    }
}
