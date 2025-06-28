package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.Chat;
import individual.individualprojectbackend.domain.Message;
import individual.individualprojectbackend.persistence.entity.ChatEntity;
import individual.individualprojectbackend.persistence.entity.MessageEntity;

import java.util.Objects;

public final class MessageConverter {

    private MessageConverter(){}


    public static Message convertToDomain(MessageEntity messageEntity, Long chatId){
        if (Objects.isNull(messageEntity)){
            return Message.builder().build();
        }

        Chat chat = Chat.builder()
                .id(chatId)
                .build();

        return Message.builder()
                .id(messageEntity.getId())
                .sender(UserConverter.convertToDomain(messageEntity.getSender()))
                .content(messageEntity.getContent())
                .chat(chat)
                .timestamp(messageEntity.getTimestamp())
                .build();
    }

    public static MessageEntity convertToEntity(Message message, Long chatId) {
        if (Objects.isNull(message)) {
            return MessageEntity.builder().build();
        }

        ChatEntity chatEntity = ChatEntity.builder()
                .id(chatId)
                .build();

        return MessageEntity.builder()
                .id(message.getId())
                .sender(UserConverter.convertToEntity(message.getSender()))
                .content(message.getContent())
                .chat(chatEntity)
                .timestamp(message.getTimestamp())
                .build();
    }
}
