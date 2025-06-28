package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.Advert;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetMostPopularAdvertsUseCaseImplTest {

    @Mock
    private AdvertRepository advertRepositoryMock;

    @InjectMocks
    private GetMostPopularAdvertsUseCaseImpl getMostPopularAdvertsUseCase;

    @Test
    void getMostPopularAdverts_successful() {
        int advertCount = 2;
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

        when(advertRepositoryMock.getMostPopularAdverts(advertCount)).thenReturn(adverts);

        List<Advert> actualAdverts = getMostPopularAdvertsUseCase.getMostPopularAdverts(advertCount);

        assertEquals(adverts.size(), actualAdverts.size());

        verify(advertRepositoryMock, times(1)).getMostPopularAdverts(advertCount);
    }

}