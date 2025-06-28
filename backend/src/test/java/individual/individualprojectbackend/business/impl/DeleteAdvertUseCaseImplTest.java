package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.persistence.AdvertRepository;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteAdvertUseCaseImplTest {

    @Mock
    AdvertRepository advertRepositoryMock;

    @InjectMocks
    DeleteAdvertUseCaseImpl deleteAdvertUseCase;

    @Test
    void deleteAdvert_successful(){
        AdvertEntity advertEntity = AdvertEntity.builder()
                .id(1L)
                .price(1000)
                .description("description")
                .utilitiesIncluded(true)
                .creationDate(LocalDate.now())
                .build();

        deleteAdvertUseCase.deleteAdvert(advertEntity.getId());

        verify(advertRepositoryMock).deleteById(advertEntity.getId());
    }

}