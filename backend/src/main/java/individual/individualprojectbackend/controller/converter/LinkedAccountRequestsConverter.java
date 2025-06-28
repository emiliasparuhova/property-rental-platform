package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateLinkedAccountRequest;
import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.domain.User;

public final class LinkedAccountRequestsConverter {
    private LinkedAccountRequestsConverter(){}


    public static LinkedAccount convertCreateRequest(CreateLinkedAccountRequest request, Long userId){
        return LinkedAccount.builder()
                .user(User.builder().id(userId).build())
                .externalAccount(ExternalAccount.builder()
                        .id(request.getId())
                        .email(request.getEmail())
                        .name(request.getName())
                        .provider(request.getProvider())
                        .build())
                .build();
    }
}
