package store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
