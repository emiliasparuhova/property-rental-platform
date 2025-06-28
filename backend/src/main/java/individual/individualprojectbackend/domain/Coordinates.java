package individual.individualprojectbackend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Coordinates {
    private double latitude;
    private double longitude;
}
