package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateAdvertRequest;
import individual.individualprojectbackend.controller.dto.GetAllAdvertsRequest;
import individual.individualprojectbackend.controller.dto.GetFilteredAdvertsCountRequest;
import individual.individualprojectbackend.controller.dto.UpdateAdvertRequest;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;
import org.springframework.stereotype.Service;

@Service
public class AdvertRequestsConverter {

    public Advert convertCreateRequest(CreateAdvertRequest request){
        return Advert.builder()
                .price(request.getPrice())
                .description(request.getDescription())
                .property(PropertyRequestsConverter.convertCreateRequest(request.getProperty()))
                .creator(request.getCreator())
                .utilitiesIncluded(request.isUtilitiesIncluded())
                .availableFrom(request.getAvailableFrom())
                .photos(request.getPhotos())
                .build();
    }

    public AdvertFilterCriteria convertGetAllRequest(GetAllAdvertsRequest request){
        return AdvertFilterCriteria.builder()
                .city(request.getCity())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .propertyType(request.getPropertyType())
                .furnishingType(request.getFurnishingType())
                .pageNumber(request.getPageNumber())
                .pageSize(request.getPageSize())
                .build();
    }

    public AdvertFilterCriteria convertGetCountRequest(GetFilteredAdvertsCountRequest request){
        return AdvertFilterCriteria.builder()
                .city(request.getCity())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .propertyType(request.getPropertyType())
                .furnishingType(request.getFurnishingType())
                .build();
    }

    public Advert convertUpdateRequest(UpdateAdvertRequest request){
        return Advert.builder()
                .id(request.getId())
                .price(request.getPrice())
                .description(request.getDescription())
                .property(request.getProperty())
                .utilitiesIncluded(request.isUtilitiesIncluded())
                .availableFrom(request.getAvailableFrom())
                .photos(request.getPhotos())
                .build();
    }
}
