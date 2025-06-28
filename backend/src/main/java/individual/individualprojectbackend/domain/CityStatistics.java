package individual.individualprojectbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CityStatistics {
    private int advertsCount;
    private double averageAdvertPrice;
    private double averagePropertySize;
}
