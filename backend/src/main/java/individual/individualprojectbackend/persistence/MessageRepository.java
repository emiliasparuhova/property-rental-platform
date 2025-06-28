package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
