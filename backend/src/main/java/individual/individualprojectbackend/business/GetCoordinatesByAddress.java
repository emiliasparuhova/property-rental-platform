package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Coordinates;

public interface GetCoordinatesByAddress {
    Coordinates getCoordinates(String address);
}
