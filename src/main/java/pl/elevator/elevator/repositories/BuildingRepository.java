package pl.elevator.elevator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.elevator.elevator.models.Building;

import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Long> {
    @Query("select b from Building b left join fetch b.elevators where b.buildingName = :name")
    Optional<Building> findBuildingByName(@Param("name") String name);
    @Query("select b from Building b left join fetch b.elevators where b.id = :id")
    Optional<Building> findBuildingById(@Param("id") long id);
}
