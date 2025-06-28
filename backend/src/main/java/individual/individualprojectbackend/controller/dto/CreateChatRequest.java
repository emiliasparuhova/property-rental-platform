package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class CreateChatRequest {
    private Advert advert;
    private User landlord;
    private User tenant;
}
