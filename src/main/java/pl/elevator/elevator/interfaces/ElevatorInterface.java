package pl.elevator.elevator.interfaces;

import org.springframework.http.ResponseEntity;
import pl.elevator.elevator.classes.BuildingElevator;
import pl.elevator.elevator.models.Building;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ElevatorInterface {

    ResponseEntity addNewFloor(String username, long buildingId, long elevatorId, int floor);
    ResponseEntity<List<Integer>> getNextFloor(String username, long buildingId, long elevatorId);
    ResponseEntity<List<Integer>> moveElevator(String username, long buildingId, long elevatorId);
    ResponseEntity<Integer> getCurrentFloor(String username, long buildingId, long elevatorId);
    ResponseEntity<List<BuildingElevator>> getUserBuildings(String username);
    ResponseEntity<BuildingElevator> getBuildingElevatorsInformation(String username, long buildingId);
    ResponseEntity<Map<String, List<BuildingElevator>>> getAllBuildingElevator();
    ResponseEntity<Building> addNewBuilding(Building building, Principal principal);
    ResponseEntity deleteBuilding(long id, Principal principal);
    ResponseEntity deleteElevator(String username, long buildingId, long elevatorId, Principal principal);
    ResponseEntity addElevator(String username, long buildingId, Principal principal);



}
