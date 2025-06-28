package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    AdvertEntity advert;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    List<MessageEntity> messages;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    UserEntity landlord;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    UserEntity tenant;
}
