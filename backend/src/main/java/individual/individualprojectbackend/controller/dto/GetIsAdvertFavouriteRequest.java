package individual.individualprojectbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetIsAdvertFavouriteRequest {
    private Long userId;
    private Long advertId;
}
