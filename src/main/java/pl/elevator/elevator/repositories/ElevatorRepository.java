package pl.elevator.elevator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.elevator.elevator.models.Elevator;

@Repository
public interface ElevatorRepository extends JpaRepository<Elevator,Long> {
}
