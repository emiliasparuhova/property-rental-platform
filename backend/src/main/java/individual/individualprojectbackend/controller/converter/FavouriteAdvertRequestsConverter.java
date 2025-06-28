package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateFavouriteAdvertRequest;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.domain.User;

public final class FavouriteAdvertRequestsConverter {

    private FavouriteAdvertRequestsConverter(){}


    public static FavouriteAdvert convertCreateRequest(CreateFavouriteAdvertRequest request){
        return FavouriteAdvert.builder()
                .user(User.builder().id(request.getUser().getId()).build())
                .advert(Advert.builder().id(request.getAdvert().getId()).build())
                .build();
    }
}
