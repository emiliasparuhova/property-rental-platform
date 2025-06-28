package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.ChatConverter;
import individual.individualprojectbackend.domain.Advert;
import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.ChatRepository;
import individual.individualprojectbackend.persistence.entity.ChatEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetChatUseCaseImplTest {
    @Mock
    ChatRepository chatRepositoryMock;

    @InjectMocks
    GetChatUseCaseImpl getChatUseCase;

    @Test
    void getChat_returnsChat_whenChatExists(){
        Chat chat = Chat.builder()
                .id(1L)
                .tenant(User.builder().id(1L).build())
                .landlord(User.builder().id(2L).build())
                .advert(Advert.builder().id(5L).build())
                .messages(new ArrayList<>())
                .build();

        ChatEntity chatEntity = ChatConverter.convertToEntity(chat);

        when(chatRepositoryMock.findByIdWithMessagesOrderedByTimestamp(chat.getId())).thenReturn(Optional.ofNullable(chatEntity));

        Chat actualChat = getChatUseCase.getChat(chat.getId());

        assertNotNull(actualChat);
        assertEquals(chat.getId(), actualChat.getId());

        verify(chatRepositoryMock).findByIdWithMessagesOrderedByTimestamp(chat.getId());
    }

    @Test
    void getChat_returnsNull_whenChatDoesntExist(){
        when(chatRepositoryMock.findByIdWithMessagesOrderedByTimestamp(50L)).thenReturn(Optional.empty());

        Chat result = getChatUseCase.getChat(50L);

        assertNull(result);
        verify(chatRepositoryMock).findByIdWithMessagesOrderedByTimestamp(50L);
    }

}