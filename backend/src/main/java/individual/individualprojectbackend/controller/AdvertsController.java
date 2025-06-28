package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.*;
import individual.individualprojectbackend.controller.converter.AdvertRequestsConverter;
import individual.individualprojectbackend.controller.dto.*;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;
import individual.individualprojectbackend.domain.CityStatistics;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/adverts")
@AllArgsConstructor
public class AdvertsController {
    private final GetAdvertUseCase getAdvertUseCase;
    private final GetAllAdvertsUseCase getAllAdvertsUseCase;
    private final CreateAdvertUseCase createAdvertUseCase;
    private final UpdateAdvertUseCase updateAdvertUseCase;
    private final DeleteAdvertUseCase deleteAdvertUseCase;
    private final GetAdvertsByLandlord getAdvertsByLandlord;
    private final GetCityStatisticsUseCase getCityStatisticsUseCase;
    private final GetAdvertsCountUseCase getAdvertsCountUseCase;
    private final GetMostPopularAdvertsUseCase getMostPopularAdvertsUseCase;

    private final AdvertRequestsConverter advertRequestsConverter;

    @GetMapping
    public ResponseEntity<GetAllAdvertsResponse> getAdverts(@ModelAttribute GetAllAdvertsRequest request){
        AdvertFilterCriteria filterCriteria = advertRequestsConverter.convertGetAllRequest(request);

        final GetAllAdvertsResponse response = GetAllAdvertsResponse.builder()
                .adverts(getAllAdvertsUseCase.getAllAdverts(filterCriteria).toArray(Advert[]::new))
                .build();

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("{id}")
    public ResponseEntity<Advert> getAdvert(@PathVariable(value = "id") final long id){
        final GetAdvertRequest request = GetAdvertRequest.builder()
                .id(id)
                .build();

        final GetAdvertResponse response = GetAdvertResponse.builder()
                .advert(getAdvertUseCase.getAdvert(request.getId()))
                .build();

        if (Objects.isNull(response.getAdvert())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(response.getAdvert());
    }

    @GetMapping("/user/{landlordId}")
    @RolesAllowed({"LANDLORD"})
    public ResponseEntity<List<Advert>> getAdvertsByLandlord(@PathVariable(value = "landlordId") final long landlordId){
        final List<Advert> adverts = getAdvertsByLandlord.getAdvertsByLandlord(landlordId);

        if (Objects.isNull(adverts)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(adverts);
    }

    @GetMapping("/statistics/{city}")
    public ResponseEntity<CityStatistics> getStatisticsByAdvertCity(@PathVariable(value = "city") final String city){
        final CityStatistics statistics = getCityStatisticsUseCase.getCityStatistics(city);

        if (Objects.isNull(statistics)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(statistics);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getAdvertsCount(@ModelAttribute GetFilteredAdvertsCountRequest request){
        AdvertFilterCriteria filterCriteria = advertRequestsConverter.convertGetCountRequest(request);

        final Integer count = getAdvertsCountUseCase.getAdvertsCount(filterCriteria);

        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/most-popular")
    public ResponseEntity<List<Advert>> getMostPopular(@RequestParam final int advertCount){
        final List<Advert> adverts = getMostPopularAdvertsUseCase.getMostPopularAdverts(advertCount);

        return ResponseEntity.ok().body(adverts);
    }

    @PostMapping()
    @RolesAllowed({"LANDLORD"})
    public ResponseEntity<CreateAdvertResponse> createAdvert(@RequestBody @Valid CreateAdvertRequest request) {
        final Advert advert = advertRequestsConverter.convertCreateRequest(request);

        final CreateAdvertResponse response = CreateAdvertResponse.builder()
                .id(createAdvertUseCase.createAdvert(advert))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"LANDLORD"})
    public ResponseEntity<Void> deleteAdvert(@PathVariable(value = "id") final long id) {
        deleteAdvertUseCase.deleteAdvert(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @RolesAllowed({"LANDLORD"})
    public ResponseEntity<Void> updateAdvert(@Valid @PathVariable("id") final long id,
                                               @RequestBody UpdateAdvertRequest request){
        request.setId(id);
        final Advert advert = advertRequestsConverter.convertUpdateRequest(request);

        updateAdvertUseCase.updateAdvert(advert);
        return ResponseEntity.noContent().build();
    }
}
