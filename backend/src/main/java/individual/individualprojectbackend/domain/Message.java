package individual.individualprojectbackend.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Message {
    private Long id;
    private User sender;
    private String content;
    private Chat chat;
    private LocalDateTime timestamp;
}
