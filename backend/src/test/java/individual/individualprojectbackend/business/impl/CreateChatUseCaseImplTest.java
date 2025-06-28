package individual.individualprojectbackend.business.impl;

import individual.individualprojectbackend.business.converter.ChatConverter;
import individual.individualprojectbackend.business.exception.ExistingChatException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateChatUseCaseImplTest {

    @Mock
    ChatRepository chatRepositoryMock;

    @InjectMocks
    CreateChatUseCaseImpl createChatUseCase;


    @Test
    void createChat_returnsChatId_whenSuccessful(){
        Chat chat = Chat.builder()
                .id(1L)
                .tenant(User.builder().id(1L).build())
                .landlord(User.builder().id(2L).build())
                .advert(Advert.builder().id(5L).build())
                .messages(new ArrayList<>())
                .build();

        ChatEntity chatEntity = ChatConverter.convertToEntity(chat);

        assert chatEntity != null;
        when(chatRepositoryMock.save(chatEntity)).thenReturn(chatEntity);

        Long actualResult = createChatUseCase.createChat(chat);
        Long expectedResult = 1L;

        assertEquals(expectedResult, actualResult);
        verify(chatRepositoryMock, times(1)).save(chatEntity);
    }

    @Test
    void createChat_throwsExistingChatException_whenChatExists(){
        Long advertID = 1L;
        Long tenantID = 3L;

        Chat chat = Chat.builder()
                .advert(Advert.builder().id(advertID).build())
                .tenant(User.builder().id(tenantID).build())
                .build();

        when(chatRepositoryMock.existsByAdvertIdAndTenantId(advertID, tenantID)).thenReturn(true);

        assertThrows(ExistingChatException.class, () -> createChatUseCase.createChat(chat));

        verify(chatRepositoryMock).existsByAdvertIdAndTenantId(advertID, tenantID);
    }

}