package individual.individualprojectbackend.business.converter;

import individual.individualprojectbackend.domain.User;
import individual.individualprojectbackend.persistence.entity.UserEntity;

import java.util.Objects;

public final class UserConverter {

    private UserConverter(){

    }

    public static User convertToDomain(UserEntity userEntity){
        if (Objects.isNull(userEntity)) {
            return User.builder().build();
        }

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .description(userEntity.getDescription())
                .hashedPassword(CredentialsConverter.convertToString(userEntity.getCredentials()))
                .address(AddressConverter.convertToDomain(userEntity.getAddress()))
                .gender(userEntity.getGender())
                .birthDate(userEntity.getBirthDate())
                .profilePicture(userEntity.getProfilePicture())
                .joinDate(userEntity.getJoinDate())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .build();
    }

    public static UserEntity convertToEntity(User user){
        if (Objects.isNull(user)) {
            return UserEntity.builder().build();
        }

        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .description(user.getDescription())
                .credentials(CredentialsConverter.convertToEntity(user.getHashedPassword()))
                .address(AddressConverter.convertToEntity(user.getAddress()))
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .profilePicture(user.getProfilePicture())
                .joinDate(user.getJoinDate())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
