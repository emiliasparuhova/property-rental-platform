package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.AdvertConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.persistence.AdvertRepository;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdvertsByLandlordImplTest {


    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    GetAdvertsByLandlordImpl getAdvertsByLandlord;

    @Test
    void getAdvertsByLandlord_successful() {
        Long landlordId = 1L;
        List<Advert> mockAdverts = Arrays.asList(
                Advert.builder().build(),
                Advert.builder().build()
        );

        List<AdvertEntity> mockEntities = mockAdverts.stream()
                        .map(AdvertConverter::convertToEntity)
                        .toList();

        when(advertRepositoryMock.findByCreator_IdOrderByCreationDateDesc(landlordId)).thenReturn(mockEntities);

        List<Advert> result = getAdvertsByLandlord.getAdvertsByLandlord(landlordId);

        assertEquals(mockAdverts.size(), result.size());

        verify(advertRepositoryMock).findByCreator_IdOrderByCreationDateDesc(landlordId);
    }
}
