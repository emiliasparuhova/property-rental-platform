package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Chat;

public interface CreateChatUseCase {
    Long createChat(Chat chat);
}
