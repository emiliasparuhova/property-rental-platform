package individual.individualprojectbackend.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "advert")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AdvertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private double price;

    @Column(name = "description", length = 3000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity creator;

    @Column(name = "utilities_included", columnDefinition = "BOOLEAN")
    private boolean utilitiesIncluded;

    @Column(name = "available_from", columnDefinition = "DATE")
    private LocalDate availableFrom;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "advert")
    private List<PhotoEntity> photos;
}


