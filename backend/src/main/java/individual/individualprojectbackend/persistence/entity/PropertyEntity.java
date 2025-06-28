package individual.individualprojectbackend.persistence.entity;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "size")
    private int size;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "furnishing_type")
    private PropertyFurnishingType furnishingType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;
}
