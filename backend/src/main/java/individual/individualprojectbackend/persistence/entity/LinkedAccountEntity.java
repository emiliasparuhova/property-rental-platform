package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "linked_account")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LinkedAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "linked_id")
    private String linkedId;

    @Column(name = "linked_email")
    private String linkedEmail;

    @Column(name = "linked_name")
    private String linkedName;

    @Column(name = "provider")
    private String provider;
}
