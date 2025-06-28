package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetChatUseCase;
import individual.individualprojectbackend.business.converter.ChatConverter;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.persistence.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class GetChatUseCaseImpl implements GetChatUseCase {
    private ChatRepository chatRepository;
    @Override
    public Chat getChat(Long id) {
        Chat chat = chatRepository.findByIdWithMessagesOrderedByTimestamp(id)
                .map(ChatConverter::convertToDomain)
                .orElse(null);

        if (Objects.isNull(chat)){
            return null;
        }

        return chat;
    }
}
