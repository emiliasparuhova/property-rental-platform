package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.MessageEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageConverterTest {

    @Test
    void convertToDomain_successful() {
        MessageEntity mockMessageEntity = mock(MessageEntity.class);
        when(mockMessageEntity.getId()).thenReturn(1L);
        when(mockMessageEntity.getSender()).thenReturn(mock(UserEntity.class));
        when(mockMessageEntity.getContent()).thenReturn("MockContent");
        when(mockMessageEntity.getTimestamp()).thenReturn(LocalDateTime.now());

        Message convertedMessage = MessageConverter.convertToDomain(mockMessageEntity, 1L);

        assertEquals(1L, convertedMessage.getId());
        assertNotNull(convertedMessage.getSender());
        assertEquals("MockContent", convertedMessage.getContent());
        assertNotNull(convertedMessage.getChat());
        assertEquals(1L, convertedMessage.getChat().getId());
        assertNotNull(convertedMessage.getTimestamp());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        Message convertedMessage = MessageConverter.convertToDomain(null, 1L);

        assertNotNull(convertedMessage);
        assertNull(convertedMessage.getId());
        assertNull(convertedMessage.getSender());
        assertNull(convertedMessage.getContent());
        assertNull(convertedMessage.getChat());
        assertNull(convertedMessage.getTimestamp());
    }

    @Test
    void convertToEntity_successful() {
        Message mockMessage = mock(Message.class);
        when(mockMessage.getId()).thenReturn(1L);
        when(mockMessage.getSender()).thenReturn(mock(User.class));
        when(mockMessage.getContent()).thenReturn("MockContent");
        when(mockMessage.getTimestamp()).thenReturn(LocalDateTime.now());

        MessageEntity convertedMessageEntity = MessageConverter.convertToEntity(mockMessage, 1L);

        assertEquals(1L, convertedMessageEntity.getId());
        assertNotNull(convertedMessageEntity.getSender());
        assertEquals("MockContent", convertedMessageEntity.getContent());
        assertNotNull(convertedMessageEntity.getChat());
        assertEquals(1L, convertedMessageEntity.getChat().getId());
        assertNotNull(convertedMessageEntity.getTimestamp());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        MessageEntity convertedMessageEntity = MessageConverter.convertToEntity(null, 1L);

        assertNotNull(convertedMessageEntity);
        assertNull(convertedMessageEntity.getId());
        assertNull(convertedMessageEntity.getSender());
        assertNull(convertedMessageEntity.getContent());
        assertNull(convertedMessageEntity.getChat());
        assertNull(convertedMessageEntity.getTimestamp());
    }

}