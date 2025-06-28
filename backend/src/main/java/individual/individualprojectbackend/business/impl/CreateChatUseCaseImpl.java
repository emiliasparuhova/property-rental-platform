package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateChatUseCase;
import individual.individualprojectbackend.business.converter.ChatConverter;
import individual.individualprojectbackend.business.exception.ExistingChatException;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.persistence.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CreateChatUseCaseImpl implements CreateChatUseCase {
    private ChatRepository chatRepository;

    @Override
    public Long createChat(Chat chat) {
        if (chatRepository.existsByAdvertIdAndTenantId(chat.getAdvert().getId(),
        chat.getTenant().getId())){
            throw new ExistingChatException();
        }
        return chatRepository.save(Objects.requireNonNull(ChatConverter.convertToEntity(chat))).getId();
    }
}
