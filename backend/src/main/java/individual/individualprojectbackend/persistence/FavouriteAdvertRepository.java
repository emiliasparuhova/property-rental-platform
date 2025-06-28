package individual.individualprojectbackend.persistence;

import individual.individualprojectbackend.persistence.entity.FavouriteAdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteAdvertRepository extends JpaRepository<FavouriteAdvertEntity, Long> {
    List<FavouriteAdvertEntity> findByUser_Id(Long userId);
    boolean existsByUser_IdAndAdvert_Id(Long userId, Long advertId);
    void deleteByUser_IdAndAdvert_Id(Long userId, Long advertId);
}
