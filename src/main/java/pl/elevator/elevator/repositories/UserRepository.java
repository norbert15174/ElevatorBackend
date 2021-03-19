package pl.elevator.elevator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.elevator.elevator.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u left join fetch u.buildings b left join b.elevators")
    Optional<List<User>> getAllUserBuildings();
    @Query("select u from User u left join fetch u.buildings b left join b.elevators where u.username = :name")
    Optional<User> getUserBuildings(@Param("name") String name);
    User findAllByUsername(String username);

    @Query("select u from User u where u.username = :username")
    Optional<User> findFirstByUsername(String username);
}
