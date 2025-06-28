package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Coordinates;
import individual.individualprojectbackend.external.dto.GeocodingDTO;
public final class CoordinatesConverter {

    private CoordinatesConverter(){}

    public static Coordinates convertToDomain(GeocodingDTO geocodingDTO){
        return Coordinates.builder()
                .latitude(geocodingDTO.getLatitude())
                .longitude(geocodingDTO.getLongitude())
                .build();
    }
}
