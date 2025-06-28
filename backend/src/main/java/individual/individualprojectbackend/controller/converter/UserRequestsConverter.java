package individual.individualprojectbackend.controller.converter;

import individual.individualprojectbackend.controller.dto.CreateUserRequest;
import individual.individualprojectbackend.controller.dto.UpdateUserRequest;
import individual.individualprojectbackend.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserRequestsConverter {

    public User convertCreateRequest(CreateUserRequest request){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .joinDate(LocalDate.now())
                .role(request.getRole())
                .build();
    }

    public User convertUpdateRequest(UpdateUserRequest request){
        return User.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .description(request.getDescription())
                .address(request.getAddress())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .profilePicture(request.getProfilePicture())
                .status(request.getStatus())
                .hashedPassword(request.getPlainTextPassword())
                .build();
    }

}
