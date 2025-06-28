package individual.individualprojectbackend.controller.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticateUserResponse {
    private String accessToken;
}
