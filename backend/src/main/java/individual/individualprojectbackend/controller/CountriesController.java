package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.GetCitiesByCountryUseCase;
import individual.individualprojectbackend.domain.City;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/countries")
@AllArgsConstructor
public class CountriesController {

    private final GetCitiesByCountryUseCase getCitiesByCountryCode;


    @GetMapping("{countryCode}")
    public ResponseEntity<List<City>> getCitiesByCountry(@PathVariable(value = "countryCode") final String countryCode) {
        City[] citiesArray = getCitiesByCountryCode.getCitiesByCountryCode(countryCode);

        if (Objects.isNull(citiesArray)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(Arrays.asList(citiesArray));
    }
}
