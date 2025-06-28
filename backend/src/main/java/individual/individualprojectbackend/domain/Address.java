package individual.individualprojectbackend.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private Long id;
    private String city;
    private String street;
    private String zipcode;
}
