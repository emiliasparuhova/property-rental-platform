package individual.individualprojectbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class CreateAddressRequest {
    private Long id;
    @NotBlank(message = "The city field is mandatory")
    private String city;
    @NotBlank(message = "The street field is mandatory")
    private String street;
    @NotBlank(message = "The zipcode field is mandatory")
    private String zipcode;
}
