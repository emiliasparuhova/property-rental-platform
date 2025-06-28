package individual.individualprojectbackend.external;

import individual.individualprojectbackend.external.dto.GeocodingDTO;

public interface GeocodingClient {
    GeocodingDTO getCoordinates(String address);

}
