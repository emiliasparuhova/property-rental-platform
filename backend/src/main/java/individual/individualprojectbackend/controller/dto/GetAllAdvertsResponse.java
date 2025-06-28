package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Advert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetAllAdvertsResponse {
    private Advert[] adverts;
}
