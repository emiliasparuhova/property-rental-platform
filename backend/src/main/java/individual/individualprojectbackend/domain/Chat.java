package individual.individualprojectbackend.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Chat {
    private Long id;
    private Advert advert;
    private List<Message> messages;
    private User landlord;
    private User tenant;
}
