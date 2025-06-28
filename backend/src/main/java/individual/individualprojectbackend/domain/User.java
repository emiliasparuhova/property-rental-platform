package individual.individualprojectbackend.domain;

import individual.individualprojectbackend.domain.enums.Gender;
import individual.individualprojectbackend.domain.enums.UserRole;
import individual.individualprojectbackend.domain.enums.UserStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private Long id;
    private String name;
    private String email;
    private String description;
    private String hashedPassword;
    private Address address;
    private Gender gender;
    private LocalDate birthDate;
    private byte[] profilePicture;
    private LocalDate joinDate;
    private UserRole role;
    private UserStatus status;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
