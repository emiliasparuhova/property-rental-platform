package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLandlordResponseRateImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    GetLandlordResponseRateImpl getLandlordResponseRate;


    @Test
    void getLandlordResponseRate_success(){
        Long landlordId = 1L;
        when(userRepository.getLandlordResponseRate(landlordId)).thenReturn(75.0);

        Double responseRate = getLandlordResponseRate.getLandlordResponseRate(landlordId);

        assertEquals(75.0, responseRate);
    }
}