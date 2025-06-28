package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetUserResponse {
    private User user;
}
