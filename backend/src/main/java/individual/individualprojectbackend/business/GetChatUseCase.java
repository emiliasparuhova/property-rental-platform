package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Chat;

public interface GetChatUseCase {

    Chat getChat(Long id);
}
