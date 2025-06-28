package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.GetChatsByUserId;
import individual.individualprojectbackend.business.converter.ChatConverter;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.persistence.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetChatsByUserIdImpl implements GetChatsByUserId {

    private ChatRepository chatRepository;
    @Override
    public List<Chat> getChatsByUser(Long userId) {
        return chatRepository.findByUserId(userId).stream()
                .map(ChatConverter::convertToDomain)
                .toList();
    }
}
