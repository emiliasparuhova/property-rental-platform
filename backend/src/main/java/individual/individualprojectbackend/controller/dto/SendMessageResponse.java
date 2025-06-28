package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class SendMessageResponse {
    private User sender;
    private String content;
    private LocalDateTime timestamp;
    private Chat chat;
}
