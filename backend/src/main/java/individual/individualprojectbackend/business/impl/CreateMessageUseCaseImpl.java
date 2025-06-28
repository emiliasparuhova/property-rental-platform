package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.CreateMessageUseCase;
import individual.individualprojectbackend.business.converter.MessageConverter;
import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.persistence.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CreateMessageUseCaseImpl implements CreateMessageUseCase {

    private MessageRepository messageRepository;

    @Override
    public Long createMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(Objects.requireNonNull(
                MessageConverter.convertToEntity(message, message.getChat().getId()))).getId();
    }
}
