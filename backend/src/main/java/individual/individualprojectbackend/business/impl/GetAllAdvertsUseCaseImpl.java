package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetAllAdvertsUseCase;
import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;
import individual.individualprojectbackend.persistence.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAdvertsUseCaseImpl implements GetAllAdvertsUseCase {
    private AdvertRepository advertRepository;
    @Override
    public List<Advert> getAllAdverts(AdvertFilterCriteria filterCriteria) {
        Pageable pageable = PageRequest.of(filterCriteria.getPageNumber(), filterCriteria.getPageSize());

        return advertRepository.findByFilters(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                        filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                        filterCriteria.getFurnishingType(), pageable)
                .stream()
                .map(AdvertConverter::convertToDomain)
                .toList();
    }
}
