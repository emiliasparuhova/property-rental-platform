package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateMessageRequest;
import individual.individualprojectbackend.domain.Message;

public final class MessageRequestsConverter {

    private MessageRequestsConverter() {}


    public static Message convertCreateRequest(CreateMessageRequest request){
        return Message.builder()
                .sender(request.getSender())
                .content(request.getContent())
                .chat(request.getChat())
                .build();
    }

}
