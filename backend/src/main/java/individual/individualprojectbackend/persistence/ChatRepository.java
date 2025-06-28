package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.persistence.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT DISTINCT c FROM ChatEntity c JOIN FETCH c.messages m WHERE c.id = :chatId ORDER BY m.timestamp ASC")
    Optional<ChatEntity> findByIdWithMessagesOrderedByTimestamp(@Param("chatId") Long chatId);
    boolean existsByAdvertIdAndTenantId(Long advertId, Long tenantId);
    @Query("SELECT c FROM ChatEntity c JOIN FETCH c.messages m " +
            "WHERE c.landlord.id = :userId OR c.tenant.id = :userId " +
            "ORDER BY m.timestamp DESC")
    List<ChatEntity> findByUserId(@Param("userId") Long userId);

}
