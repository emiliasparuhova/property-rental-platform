package individual.individualprojectbackend.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
