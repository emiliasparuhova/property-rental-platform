package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.ExternalAccount;
import individual.individualprojectbackend.external.dto.OAuth2UserDTO;

public final class ExternalAccountConverter {

    private ExternalAccountConverter(){}

    public static ExternalAccount convertToDomain(OAuth2UserDTO userDTO){
        return ExternalAccount.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .provider(userDTO.getProvider())
                .build();
    }
}
