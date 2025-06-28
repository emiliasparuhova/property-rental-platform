package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT COALESCE((" +
            "COUNT(DISTINCT m.chat.id) * 100.0 / COUNT(DISTINCT c.id)" +
            "), 0) " +
            "FROM UserEntity u " +
            "JOIN ChatEntity c ON u.id = c.landlord.id " +
            "LEFT JOIN MessageEntity m ON c.id = m.chat.id AND m.sender.id = u.id " +
            "WHERE u.id = :landlordId")
    Double getLandlordResponseRate(@Param("landlordId") Long landlordId);




}
