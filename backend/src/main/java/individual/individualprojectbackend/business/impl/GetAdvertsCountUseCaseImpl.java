package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetAdvertsCountUseCase;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAdvertsCountUseCaseImpl implements GetAdvertsCountUseCase {
    private AdvertRepository advertRepository;

    @Override
    public int getAdvertsCount(AdvertFilterCriteria filterCriteria) {
        return advertRepository.countAdverts(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                filterCriteria.getFurnishingType()).orElse(0);
    }
}
