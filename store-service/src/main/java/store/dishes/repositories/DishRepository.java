package store.dishes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.dishes.entity.Category;
import store.dishes.entity.Dish;

import java.util.List;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByNameContainingIgnoreCase(String name);

    boolean existsByNameContainsIgnoreCase(String name);

    List<Dish> findAllByCategory(Category category);
}
