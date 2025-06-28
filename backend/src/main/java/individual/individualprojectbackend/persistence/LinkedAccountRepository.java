package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.persistence.entity.LinkedAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccountEntity, Long> {
    List<LinkedAccountEntity> findByUser_Id(Long userId);

    Optional<LinkedAccountEntity> findFirstByLinkedId(String linkedId);

    boolean existsByUser_IdAndProvider(Long userId, String provider);
    boolean existsByLinkedId(String linkedId);

}
