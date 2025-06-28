package individual.individualprojectbackend.business;

import individual.individualprojectbackend.domain.Chat;

import java.util.List;

public interface GetChatsByUserId {

    List<Chat> getChatsByUser(Long userId);
}
