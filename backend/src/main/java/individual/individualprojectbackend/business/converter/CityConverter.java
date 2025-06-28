package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.City;
import individual.individualprojectbackend.external.dto.CityDTO;

public final class CityConverter {

    private CityConverter(){

    }

    public static City convertToDomain(CityDTO cityDTO){
        return City.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .build();
    }
}
