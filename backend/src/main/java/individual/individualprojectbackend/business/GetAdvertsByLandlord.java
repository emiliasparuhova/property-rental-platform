package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Advert;

import java.util.List;

public interface GetAdvertsByLandlord {
    List<Advert> getAdvertsByLandlord(Long landlordId);
}
