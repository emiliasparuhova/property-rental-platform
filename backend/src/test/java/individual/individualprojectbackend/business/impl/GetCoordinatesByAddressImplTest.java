package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.Coordinates;
import individual.individualprojectbackend.external.GeocodingClient;
import individual.individualprojectbackend.external.dto.GeocodingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCoordinatesByAddressImplTest {

    @Mock
    GeocodingClient geocodingClient;

    @InjectMocks
    GetCoordinatesByAddressImpl getCoordinatesByAddress;


    @Test
    void getCoordinatesByAddress_successful(){
        String address = "Eindhoven, Boschdijk 454, 5622 PC ";

        GeocodingDTO geocodingDTO = GeocodingDTO.builder()
                .longitude(5)
                .latitude(51)
                .build();

        when(geocodingClient.getCoordinates(address)).thenReturn(geocodingDTO);

        Coordinates result = getCoordinatesByAddress.getCoordinates(address);

        assertEquals(5, result.getLongitude());
        assertEquals(51, result.getLatitude());

        verify(geocodingClient).getCoordinates(address);
    }



}