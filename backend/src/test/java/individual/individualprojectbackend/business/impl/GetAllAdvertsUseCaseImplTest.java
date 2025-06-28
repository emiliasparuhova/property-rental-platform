package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.AdvertFilterCriteria;
import individual.individualprojectbackend.persistence.AdvertRepository;
import individual.individualprojectbackend.persistence.entity.AddressEntity;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.PropertyEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllAdvertsUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    GetAllAdvertsUseCaseImpl getAllAdvertsUseCase;

    @Test
    void getAllAdverts_successful(){
        List<AdvertEntity> adverts = new ArrayList<>();
        adverts.add(AdvertEntity.builder()
                .id(1L)
                .price(1000)
                .description("description")
                .utilitiesIncluded(true)
                .creationDate(LocalDate.now())
                .property(PropertyEntity.builder().address(AddressEntity.builder().build()).build())
                .creator(UserEntity.builder().address(AddressEntity.builder().build()).build())
                .photos(new ArrayList<>())
                .build());
        adverts.add(AdvertEntity.builder()
                .id(1L)
                .price(1000)
                .description("description")
                .utilitiesIncluded(true)
                .creationDate(LocalDate.now())
                .property(PropertyEntity.builder().address(AddressEntity.builder().build()).build())
                .creator(UserEntity.builder().address(AddressEntity.builder().build()).build())
                .photos(new ArrayList<>())
                .build());

        AdvertFilterCriteria filterCriteria = AdvertFilterCriteria.builder()
                .pageSize(10)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        Page<AdvertEntity> advertsPage = new PageImpl<>(adverts, pageable, adverts.size());

        when(advertRepositoryMock.findByFilters(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                filterCriteria.getFurnishingType(), pageable)).thenReturn(advertsPage);

        List<Advert> actualAdverts = getAllAdvertsUseCase.getAllAdverts(filterCriteria);

        assertNotNull(actualAdverts);
        assertEquals(2, actualAdverts.size());

        verify(advertRepositoryMock, times(1)).findByFilters(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                filterCriteria.getFurnishingType(), pageable);
    }

}