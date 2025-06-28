package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.GetCoordinatesByAddress;
import individual.individualprojectbackend.domain.Coordinates;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geocoding")
@AllArgsConstructor
public class GeocodingController {
    private GetCoordinatesByAddress getCoordinatesByAddress;

    @GetMapping("{address}")
    public ResponseEntity<Coordinates> getCoordinates(@PathVariable("address") final String address){
        final Coordinates coordinates = getCoordinatesByAddress.getCoordinates(address);

        return ResponseEntity.ok().body(coordinates);
    }
}
