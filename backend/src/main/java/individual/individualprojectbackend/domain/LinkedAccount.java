package individual.individualprojectbackend.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LinkedAccount {
    private Long id;
    private User user;
    private ExternalAccount externalAccount;
}
