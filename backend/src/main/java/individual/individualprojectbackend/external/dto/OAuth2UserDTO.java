package individual.individualprojectbackend.external.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserDTO {
    private String id;
    private String email;
    private String name;
    private String provider;
}
