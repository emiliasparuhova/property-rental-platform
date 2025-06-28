package individual.individualprojectbackend.controller.dto;

import individual.individualprojectbackend.domain.Property;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateAdvertRequest {
    private Long id;
    @DecimalMin("0.0")
    private double price;
    @NotBlank
    private String description;
    @NotNull
    private Property property;
    @NotNull
    private boolean utilitiesIncluded;
    @NotNull
    private LocalDate availableFrom;
    private List<byte[]> photos;
}
