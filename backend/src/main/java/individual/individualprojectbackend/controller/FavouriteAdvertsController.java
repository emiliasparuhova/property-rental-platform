package individual.individualprojectbackend.controller;

import individual.individualprojectbackend.business.CreateFavouriteAdvertUseCase;
import individual.individualprojectbackend.business.DeleteFavouriteAdvertUseCase;
import individual.individualprojectbackend.business.GetFavouriteAdvertsUseCase;
import individual.individualprojectbackend.business.GetIsAdvertFavouriteUseCase;
import individual.individualprojectbackend.controller.converter.FavouriteAdvertRequestsConverter;
import individual.individualprojectbackend.controller.dto.*;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite-adverts")
@AllArgsConstructor
public class FavouriteAdvertsController {

    private CreateFavouriteAdvertUseCase createFavouriteAdvertUseCase;
    private GetFavouriteAdvertsUseCase getFavouriteAdvertsUseCase;
    private DeleteFavouriteAdvertUseCase deleteFavouriteAdvertUseCase;
    private GetIsAdvertFavouriteUseCase getIsAdvertFavouriteUseCase;

    @GetMapping
    @RolesAllowed({"TENANT"})
    public ResponseEntity<List<FavouriteAdvert>> getFavouriteAdverts(@ModelAttribute GetFavouriteAdvertsRequest request){
        final List<FavouriteAdvert> favouriteAdverts = getFavouriteAdvertsUseCase.getFavouriteAdverts(request.getUserId());

        return ResponseEntity.ok().body(favouriteAdverts);
    }

    @GetMapping("/isFavourite")
    @RolesAllowed({"TENANT"})
    public ResponseEntity<Boolean> isAdvertFavourite(@ModelAttribute GetIsAdvertFavouriteRequest request){
        final Boolean isAdvertFavourite = getIsAdvertFavouriteUseCase.getIsAdvertFavourite(request.getUserId(), request.getAdvertId());

        return ResponseEntity.ok().body(isAdvertFavourite);
    }

    @PostMapping()
    @RolesAllowed({"TENANT"})

    public ResponseEntity<Long> createFavouriteAdvert(@RequestBody CreateFavouriteAdvertRequest request) {
        final FavouriteAdvert favouriteAdvert = FavouriteAdvertRequestsConverter.convertCreateRequest(request);

        final Long response = createFavouriteAdvertUseCase.createFavouriteAdvert(favouriteAdvert);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{userId}/{advertId}")
    @RolesAllowed({"TENANT"})
    @Transactional
    public ResponseEntity<Void> deleteFavouriteAdvert(
            @PathVariable(value = "userId") final Long userId,
            @PathVariable(value = "advertId") final Long advertId
    ) {
        deleteFavouriteAdvertUseCase.deleteFavouriteAdvert(userId, advertId);
        return ResponseEntity.noContent().build();
    }

}
