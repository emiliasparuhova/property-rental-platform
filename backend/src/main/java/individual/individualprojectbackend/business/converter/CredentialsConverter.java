package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.persistence.entity.CredentialsEntity;

import java.util.Objects;

public final class CredentialsConverter {

    private CredentialsConverter(){

    }

    public static String convertToString(CredentialsEntity credentialsEntity){
        if (Objects.isNull(credentialsEntity)){
            return "";
        }
        return credentialsEntity.getHashedPassword();
    }

    public static CredentialsEntity convertToEntity(String hashedPassword){
        if (Objects.isNull(hashedPassword)){
            return CredentialsEntity.builder().build();
        }
        return CredentialsEntity.builder()
                .hashedPassword(hashedPassword)
                .build();
    }
}
