package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "message")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    UserEntity sender;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    ChatEntity chat;

    @Column(name = "timestamp", columnDefinition = "DATE")
    private LocalDateTime timestamp;
}
