package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsEntity {
    @Column(name = "hashed_password")
    private String hashedPassword;
}
