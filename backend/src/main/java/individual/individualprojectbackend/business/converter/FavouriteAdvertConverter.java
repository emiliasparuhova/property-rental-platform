package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.FavouriteAdvert;
import individual.individualprojectbackend.persistence.entity.FavouriteAdvertEntity;

import java.util.Objects;

public final class FavouriteAdvertConverter {

    private FavouriteAdvertConverter(){}

    public static FavouriteAdvert convertToDomain(FavouriteAdvertEntity favouriteAdvertEntity){
        if (Objects.isNull(favouriteAdvertEntity)){
            return FavouriteAdvert.builder().build();
        }

        return FavouriteAdvert.builder()
                .user(UserConverter.convertToDomain(favouriteAdvertEntity.getUser()))
                .advert(AdvertConverter.convertToDomain(favouriteAdvertEntity.getAdvert()))
                .timestamp(favouriteAdvertEntity.getTimestamp())
                .build();
    }

    public static FavouriteAdvertEntity convertToEntity(FavouriteAdvert favouriteAdvert){
        if (Objects.isNull(favouriteAdvert)){
            return FavouriteAdvertEntity.builder().build();
        }

        return FavouriteAdvertEntity.builder()
                .user(UserConverter.convertToEntity(favouriteAdvert.getUser()))
                .advert(AdvertConverter.convertToEntity(favouriteAdvert.getAdvert()))
                .timestamp(favouriteAdvert.getTimestamp())
                .build();
    }
}
