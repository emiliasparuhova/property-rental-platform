package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class CreateAdvertRequest {
    @DecimalMin(value = "0.0", message = "The price must be a positive number")
    private double price;
    @NotBlank(message = "The description field is mandatory")
    private String description;
    @Valid
    private CreatePropertyRequest property;
    private User creator;
    @NotNull(message = "The utilities included field is mandatory")
    private boolean utilitiesIncluded;
    @NotNull(message = "The available date filed is mandatory")
    private LocalDate availableFrom;
    private List<byte[]> photos;
}
