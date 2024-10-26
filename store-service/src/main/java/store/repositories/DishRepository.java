package store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import store.domain.entity.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

    boolean existsByName(String name);
}
