package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.MessageConverter;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.MessageRepository;
import individual.individualprojectbackend.persistence.entity.MessageEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMessageUseCaseImplTest {

    @Mock
    MessageRepository messageRepositoryMock;

    @InjectMocks
    CreateMessageUseCaseImpl createMessageUseCase;


    @Test
    void createMessage_returnsMessageId_whenSuccessful(){
        Message message = Message.builder()
                .id(1L)
                .sender(User.builder().id(1L).build())
                .chat(Chat.builder().id(1L).build())
                .timestamp(LocalDateTime.now())
                .content("hi")
                .build();

        MessageEntity messageEntity = MessageConverter.convertToEntity(message, message.getChat().getId());

        assert messageEntity != null;
        when(messageRepositoryMock.save(any(MessageEntity.class))).thenReturn(messageEntity);

        Long actualResult = createMessageUseCase.createMessage(message);
        Long expectedResult = 1L;

        assertEquals(expectedResult, actualResult);

        verify(messageRepositoryMock).save(any(MessageEntity.class));
    }

}