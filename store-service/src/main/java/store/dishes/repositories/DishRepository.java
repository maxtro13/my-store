package store.dishes.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.dishes.entity.Category;
import store.dishes.entity.Dish;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    boolean existsByNameContainingIgnoreCase(String name);

    boolean existsByNameContainsIgnoreCase(String name);

    List<Dish> findAllByCategory(Category category);

    @Query("SELECT d FROM Dish d LEFT JOIN FETCH d.image WHERE d.id = :id")
    Optional<Dish> findByIdWithImage(@Param("id") Long id);


    @Query("SELECT COUNT(d) = :size FROM Dish d WHERE d.id IN :ids")
    boolean existsAllByIds(@Param("ids") Set<Long> ids, @Param("size") long size);
}
