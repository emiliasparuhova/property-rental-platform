package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateFavouriteAdvertRequest {
    private User user;
    private Advert advert;
}
