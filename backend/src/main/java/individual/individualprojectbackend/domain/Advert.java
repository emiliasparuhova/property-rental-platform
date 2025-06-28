package individual.individualprojectbackend.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Advert {
    private Long id;
    private double price;
    private String description;
    private Property property;
    private User creator;
    private boolean utilitiesIncluded;
    private LocalDate availableFrom;
    private LocalDate creationDate;
    private List<byte[]> photos;
}
