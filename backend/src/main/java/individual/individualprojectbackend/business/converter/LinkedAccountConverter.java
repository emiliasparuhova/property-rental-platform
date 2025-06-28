package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.domain.LinkedAccount;
import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;

import java.util.Objects;

public final class LinkedAccountConverter {

    private LinkedAccountConverter(){}


    public static LinkedAccount convertToDomain(LinkedAccountEntity linkedAccountEntity){
        if (Objects.isNull(linkedAccountEntity)){
            return LinkedAccount.builder().build();
        }

        return LinkedAccount.builder()
                .id(linkedAccountEntity.getId())
                .user(UserConverter.convertToDomain(linkedAccountEntity.getUser()))
                .externalAccount(ExternalAccount.builder()
                        .id(linkedAccountEntity.getLinkedId())
                        .email(linkedAccountEntity.getLinkedEmail())
                        .name(linkedAccountEntity.getLinkedName())
                        .provider(linkedAccountEntity.getProvider())
                        .build())
                .build();
    }

    public static LinkedAccountEntity convertToEntity(LinkedAccount linkedAccount){
        if (Objects.isNull(linkedAccount)){
            return LinkedAccountEntity.builder().build();
        }

        return LinkedAccountEntity.builder()
                .id(linkedAccount.getId())
                .user(UserConverter.convertToEntity(linkedAccount.getUser()))
                .linkedId(linkedAccount.getExternalAccount().getId())
                .linkedEmail(linkedAccount.getExternalAccount().getEmail())
                .linkedName(linkedAccount.getExternalAccount().getName())
                .provider(linkedAccount.getExternalAccount().getProvider())
                .build();
    }
}
