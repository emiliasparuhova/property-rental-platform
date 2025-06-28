package individual.individualprojectbackend.external.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2GoogleTokenDTO {
    private String accessToken;
}
