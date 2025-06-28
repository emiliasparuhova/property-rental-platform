package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.persistence.entity.ChatEntity;
import individual.individualprojectbackend.persistence.entity.MessageEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChatConverter {

    private ChatConverter(){}


    public static Chat convertToDomain(ChatEntity chatEntity) {
        if (Objects.isNull(chatEntity)) {
            return Chat.builder().build();
        }

        List<Message> messages;

        if (Objects.isNull(chatEntity.getMessages())) {
            messages = new ArrayList<>();
        }
        else{
            messages = chatEntity.getMessages().stream()
                    .map(message -> MessageConverter.convertToDomain(message, chatEntity.getId()))
                    .toList();
        }

        return Chat.builder()
                .id(chatEntity.getId())
                .advert(AdvertConverter.convertToDomain(chatEntity.getAdvert()))
                .landlord(UserConverter.convertToDomain(chatEntity.getLandlord()))
                .tenant(UserConverter.convertToDomain(chatEntity.getTenant()))
                .messages(messages)
                .build();
    }

    public static ChatEntity convertToEntity(Chat chat) {
        if (Objects.isNull(chat)) {
            return ChatEntity.builder().build();
        }

        List<MessageEntity> messages;

        if (Objects.isNull(chat.getMessages())) {
            messages = new ArrayList<>();
        }
        else{
            messages = chat.getMessages().stream()
                    .map(message -> MessageConverter.convertToEntity(message, chat.getId()))
                    .toList();
        }

        return ChatEntity.builder()
                .id(chat.getId())
                .advert(AdvertConverter.convertToEntity(chat.getAdvert()))
                .landlord(UserConverter.convertToEntity(chat.getLandlord()))
                .tenant(UserConverter.convertToEntity(chat.getTenant()))
                .messages(messages)
                .build();

    }
}
