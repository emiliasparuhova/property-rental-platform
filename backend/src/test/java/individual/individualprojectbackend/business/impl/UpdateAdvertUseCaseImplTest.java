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

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAdvertUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    UpdateAdvertUseCaseImpl updateAdvertUseCase;

    @Test
    void updateAdvert_successfulWhenAdvertIsNotNull(){
        Advert advert = Advert.builder()
                .id(1L)
                .price(1000)
                .description("description")
                .utilitiesIncluded(true)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(new ArrayList<>())
                .build();

        AdvertEntity advertEntity = AdvertConverter.convertToEntity(advert);

        Advert updatedAdvert = Advert.builder()
                .id(advert.getId())
                .price(1500)
                .description("new description")
                .utilitiesIncluded(false)
                .property(Property.builder().address(Address.builder().build()).build())
                .creator(User.builder().address(Address.builder().build()).build())
                .photos(new ArrayList<>())
                .build();

        AdvertEntity updatedAdvertEntity = AdvertConverter.convertToEntity(updatedAdvert);

        when(advertRepositoryMock.findById(advert.getId())).thenReturn(Optional.ofNullable(advertEntity));
        updateAdvertUseCase.updateAdvert(updatedAdvert);

        verify(advertRepositoryMock).save(updatedAdvertEntity);
    }

}