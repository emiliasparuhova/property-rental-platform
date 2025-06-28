package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class CreateMessageRequest {
    private User sender;
    private String content;
    private Chat chat;
}
