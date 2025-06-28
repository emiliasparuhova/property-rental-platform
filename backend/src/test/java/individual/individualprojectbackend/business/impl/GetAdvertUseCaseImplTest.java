package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.Property;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.AdvertRepository;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAdvertUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    GetAdvertUseCaseImpl getAdvertUseCase;

    @Test
    void getAdvertById_successfulWhenAdvertExists(){
        Advert advert = Advert.builder()
                .id(1L)
                .price(1000)
                .description("description")
                .utilitiesIncluded(true)
                .creationDate(LocalDate.now())
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(new ArrayList<>())
                .build();

        AdvertEntity advertEntity = AdvertConverter.convertToEntity(advert);

        when(advertRepositoryMock.findById(advert.getId())).thenReturn(Optional.ofNullable(advertEntity));

        Advert actualResult = getAdvertUseCase.getAdvert(advert.getId());

        assertNotNull(actualResult);
        assertEquals(advert.getId(), actualResult.getId());
        verify(advertRepositoryMock, times(1)).findById(advert.getId());
    }

    @Test
    void getAdvertById_returnsNull_whenAdvertDoesntExist(){
        Long id = 1L;

        when(advertRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertNull(getAdvertUseCase.getAdvert(id));
        verify(advertRepositoryMock, times(1)).findById(id);
    }

}