package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long photoId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advert_id", referencedColumnName = "id")
    private AdvertEntity advert;

    @Lob
    @Column(name = "photo_data", columnDefinition="LONGBLOB")
    private byte[] photoData;
}