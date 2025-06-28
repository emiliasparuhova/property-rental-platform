package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetAdvertsByLandlord;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAdvertsByLandlordImpl implements GetAdvertsByLandlord {

    private AdvertRepository advertRepository;
    @Override
    public List<Advert> getAdvertsByLandlord(Long landlordId) {
        return advertRepository.findByCreator_IdOrderByCreationDateDesc(landlordId).stream()
                .map(AdvertConverter::convertToDomain)
                .toList();
    }
}
