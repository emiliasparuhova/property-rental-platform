package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.persistence.ChatRepository;
import individual.individualprojectbackend.persistence.entity.ChatEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetChatsByUserIdImplTest {

    @Mock
    ChatRepository chatRepositoryMock;

    @InjectMocks
    GetChatsByUserIdImpl getChatsByUserId;


    @Test
    void getChatsByUser_returnsChats_whenChatsFound() {
        Long userId = 1L;
        List<ChatEntity> mockChats = Arrays.asList(
                ChatEntity.builder().build(),
                ChatEntity.builder().build()
        );

        when(chatRepositoryMock.findByUserId(userId)).thenReturn(mockChats);

        List<Chat> result = getChatsByUserId.getChatsByUser(userId);

        assertEquals(mockChats.size(), result.size());
        verify(chatRepositoryMock, times(1)).findByUserId(userId);
    }

    @Test
    void getChatsByUser_returnsEmptyList_whenNoChatsFound() {
        Long userId = 1L;
        when(chatRepositoryMock.findByUserId(userId)).thenReturn(List.of());

        List<Chat> result = getChatsByUserId.getChatsByUser(userId);

        assertEquals(0, result.size());
        verify(chatRepositoryMock, times(1)).findByUserId(userId);
    }

}