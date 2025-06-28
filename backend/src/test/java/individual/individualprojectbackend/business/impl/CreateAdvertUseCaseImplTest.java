package individual.individualprojectbackend.business.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.Property;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.AdvertRepository;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class CreateAdvertUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    CreateAdvertUseCaseImpl createAdvertUseCase;

    @Test
    void createAdvert_successfulWhenInputIsValid(){
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

        when(advertRepositoryMock.save(advertEntity)).thenReturn(advertEntity);

        Long actualResult = createAdvertUseCase.createAdvert(advert);
        Long expectedResult = 1L;

        assertEquals(expectedResult, actualResult);
        verify(advertRepositoryMock, times(1)).save(advertEntity);
    }


}