package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Address;
import individual.individualprojectbackend.domain.enums.Gender;
import individual.individualprojectbackend.domain.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateUserRequest {
    private Long id;
    @NotBlank
    @Length(min = 2)
    private String name;
    @NotBlank
    @Email
    private String email;
    private String description;
    private Address address;
    private Gender gender;
    private LocalDate birthDate;
    private byte[] profilePicture;
    private UserStatus status;
    private String plainTextPassword;
}
