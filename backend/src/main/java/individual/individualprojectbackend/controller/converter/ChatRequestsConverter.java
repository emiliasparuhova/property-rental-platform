package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateChatRequest;
import individual.individualprojectbackend.domain.Chat;

public final class ChatRequestsConverter {

    private ChatRequestsConverter(){}


    public static Chat convertCreateRequest(CreateChatRequest request){
        return Chat.builder()
                .advert(request.getAdvert())
                .landlord(request.getLandlord())
                .tenant(request.getTenant())
                .build();
    }
}
