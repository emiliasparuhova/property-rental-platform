package individual.individualprojectbackend.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class FavouriteAdvert {
    private User user;
    private Advert advert;
    private LocalDateTime timestamp;
}
