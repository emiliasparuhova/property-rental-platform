package individual.individualprojectbackend.domain;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ExternalAccount {
    private String id;
    private String email;
    private String name;
    private String provider;
}
