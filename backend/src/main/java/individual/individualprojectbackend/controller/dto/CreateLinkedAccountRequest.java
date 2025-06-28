package individual.individualprojectbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateLinkedAccountRequest {
    private String id;
    private String email;
    private String name;
    private String provider;
}
