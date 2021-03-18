package pl.elevator.elevator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.elevator.elevator.classes.BuildingElevator;
import pl.elevator.elevator.models.Building;
import pl.elevator.elevator.services.ElevatorService;

import java.util.List;

@RestController
@RequestMapping("/elevator")
public class ElevatorController {

    private ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @GetMapping()
    public ResponseEntity<List<Integer>> getFlats(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId) {
        return elevatorService.getNextFlats(username, buildingId, elevatorId);
    }

    @GetMapping("/add")
    public ResponseEntity<BuildingElevator> addFlats(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId, @RequestParam int flat) {
        return elevatorService.addNewFlat(username,buildingId,elevatorId,flat);
    }
    @GetMapping("/move")
    public ResponseEntity<List<Integer>> moveElevator(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId) {
        return elevatorService.moveElevator(username,buildingId,elevatorId);
    }
    @GetMapping("/flat")
    public ResponseEntity<Integer> getCurrentFlat(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId) {
        return elevatorService.getCurrentFlat(username,buildingId,elevatorId);
    }
    @GetMapping("/user/{name}")
    public ResponseEntity<List<BuildingElevator>> getUserBuildings(@PathVariable("name") String username){
        return elevatorService.getUserBuildings(username);

    }
    @GetMapping("/user/{name}/{building}")
    public ResponseEntity<BuildingElevator> getBuildingElevators(@PathVariable("name") String username, @PathVariable("building") long id){
        return elevatorService.getBuildingElevatorsInformation(username,id);

    }
}