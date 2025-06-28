package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import individual.individualprojectbackend.persistence.entity.ChatEntity;
import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChatConverterTest {

    @Test
    void convertToDomain_successful() {
        ChatEntity mockChatEntity = mock(ChatEntity.class);
        when(mockChatEntity.getId()).thenReturn(1L);
        when(mockChatEntity.getAdvert()).thenReturn(mock(AdvertEntity.class));
        when(mockChatEntity.getLandlord()).thenReturn(mock(UserEntity.class));
        when(mockChatEntity.getTenant()).thenReturn(mock(UserEntity.class));
        when(mockChatEntity.getMessages()).thenReturn(new ArrayList<>());

        Chat convertedChat = ChatConverter.convertToDomain(mockChatEntity);

        assertEquals(1L, convertedChat.getId());
        assertNotNull(convertedChat.getAdvert());
        assertNotNull(convertedChat.getLandlord());
        assertNotNull(convertedChat.getTenant());
        assertNotNull(convertedChat.getMessages());
    }

    @Test
    void convertToDomain_returnsEmptyDomain_whenEntityIsNull() {
        Chat convertedChat = ChatConverter.convertToDomain(null);

        assertNotNull(convertedChat);
        assertNull(convertedChat.getId());
        assertNull(convertedChat.getAdvert());
        assertNull(convertedChat.getLandlord());
        assertNull(convertedChat.getTenant());
        assertNull(convertedChat.getMessages());
    }

    @Test
    void convertToEntity_successful() {
        Chat mockChat = mock(Chat.class);
        when(mockChat.getId()).thenReturn(1L);
        when(mockChat.getAdvert()).thenReturn(mock(Advert.class));
        when(mockChat.getLandlord()).thenReturn(mock(User.class));
        when(mockChat.getTenant()).thenReturn(mock(User.class));
        when(mockChat.getMessages()).thenReturn(new ArrayList<>());

        ChatEntity convertedChatEntity = ChatConverter.convertToEntity(mockChat);

        assertEquals(1L, convertedChatEntity.getId());
        assertNotNull(convertedChatEntity.getAdvert());
        assertNotNull(convertedChatEntity.getLandlord());
        assertNotNull(convertedChatEntity.getTenant());
        assertNotNull(convertedChatEntity.getMessages());
    }

    @Test
    void convertToEntity_returnsEmptyEntity_whenDomainIsNull() {
        ChatEntity convertedChatEntity = ChatConverter.convertToEntity(null);

        assertNotNull(convertedChatEntity);
        assertNull(convertedChatEntity.getId());
        assertNull(convertedChatEntity.getAdvert());
        assertNull(convertedChatEntity.getLandlord());
        assertNull(convertedChatEntity.getTenant());
        assertNull(convertedChatEntity.getMessages());
    }

}