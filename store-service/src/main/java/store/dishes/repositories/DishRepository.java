package store.dishes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.dishes.entity.Category;
import store.dishes.entity.Dish;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    boolean existsByNameContainingIgnoreCase(String name);

    boolean existsByNameContainsIgnoreCase(String name);

    List<Dish> findAllByCategory(Category category);
}
