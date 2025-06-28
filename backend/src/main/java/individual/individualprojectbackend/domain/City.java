package individual.individualprojectbackend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class City {
    private Long id;
    private String name;
}
