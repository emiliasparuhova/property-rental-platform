package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.domain.enums.PropertyFurnishingType;
import individual.individualprojectbackend.domain.enums.PropertyType;
import individual.individualprojectbackend.persistence.entity.AdvertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {

    @Query("SELECT a FROM AdvertEntity a " +
            "JOIN a.property p JOIN p.address addr " +
            "WHERE (:city IS NULL or addr.city = :city) " +
            "AND (:minPrice = 0.0 or a.price >= :minPrice) " +
            "AND (:maxPrice = 0.0 or a.price <= :maxPrice) " +
            "AND (:propertyType IS NULL or p.propertyType = :propertyType) " +
            "AND (:furnishingType IS NULL or p.furnishingType = :furnishingType) " +
            "ORDER BY a.creationDate DESC")
    Page<AdvertEntity> findByFilters(@Param("city") String city, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice,
                                     @Param("propertyType") PropertyType propertyType,
                                     @Param("furnishingType") PropertyFurnishingType furnishingType,
                                     Pageable pageable);

    List<AdvertEntity> findByCreator_IdOrderByCreationDateDesc(Long creatorId);

    @Query("SELECT COUNT(a) FROM AdvertEntity a " +
            "JOIN a.property p JOIN p.address addr " +
            "WHERE (:city IS NULL or addr.city = :city) " +
            "AND (:minPrice = 0.0 or a.price >= :minPrice) " +
            "AND (:maxPrice = 0.0 or a.price <= :maxPrice) " +
            "AND (:propertyType IS NULL or p.propertyType = :propertyType) " +
            "AND (:furnishingType IS NULL or p.furnishingType = :furnishingType)")
    Optional<Integer> countAdverts(@Param("city") String city, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice,
                                   @Param("propertyType") PropertyType propertyType,
                                   @Param("furnishingType") PropertyFurnishingType furnishingType);
    @Query("SELECT COUNT(a) FROM AdvertEntity a JOIN a.property p JOIN p.address addr WHERE addr.city = :city")
    Optional<Integer> countAdvertsByCity(@Param("city") String city);

    @Query("SELECT AVG(a.price) FROM AdvertEntity a JOIN a.property p JOIN p.address addr WHERE addr.city = :city")
    Optional<Double> calculateAveragePriceByCity(@Param("city") String city);

    @Query("SELECT AVG(p.size) FROM AdvertEntity a JOIN a.property p JOIN p.address addr WHERE addr.city = :city")
    Optional<Double> calculateAveragePropertySizeByCity(@Param("city") String city);

    @Query(value = "SELECT a.* FROM advert a " +
            "JOIN chat c ON a.id = c.advert_id " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(c.advert_id) DESC " +
            "LIMIT :advertCount", nativeQuery = true)
    List<AdvertEntity> getMostPopularAdverts(@Param("advertCount") int advertCount);




}
