package individual.individualprojectbackend.business.impl;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdvertsCountUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    GetAdvertsCountUseCaseImpl getAdvertsCountUseCase;


    @Test
    void getAdvertsCount_returnsAdvertsCount_whenSuccessful(){
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
                .city("City")
                .build();

        when(advertRepositoryMock.countAdverts(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                filterCriteria.getFurnishingType()))
                .thenReturn(Optional.of(2));

        int actualResult = getAdvertsCountUseCase.getAdvertsCount(filterCriteria);
        int expectedResult = 2;

        assertEquals(expectedResult, actualResult);

        verify(advertRepositoryMock).countAdverts(filterCriteria.getCity(), filterCriteria.getMinPrice(),
                filterCriteria.getMaxPrice(), filterCriteria.getPropertyType(),
                filterCriteria.getFurnishingType());
    }

}