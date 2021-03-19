package pl.elevator.elevator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.elevator.elevator.DTO.BuildingDataDTO;
import pl.elevator.elevator.classes.BuildingElevator;
import pl.elevator.elevator.models.Building;
import pl.elevator.elevator.services.ElevatorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elevator")
@CrossOrigin
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

    @PostMapping("/add/{flat}")
    public ResponseEntity<BuildingElevator> addFlats(@RequestBody BuildingDataDTO buildingDataDTO, @PathVariable("flat") int flat) {
        return elevatorService.addNewFlat(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),buildingDataDTO.getElevatorId(),flat);
    }
    @PostMapping("/move")
    public ResponseEntity<List<Integer>> moveElevator(@RequestBody BuildingDataDTO buildingDataDTO) {
        return elevatorService.moveElevator(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),buildingDataDTO.getElevatorId());
    }
    @GetMapping("/flat")
    public ResponseEntity<Integer> getCurrentFlat(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId) {
        return elevatorService.getCurrentFlat(username,buildingId,elevatorId);
    }
    @GetMapping("/user/{name}")
    public ResponseEntity<List<BuildingElevator>> getUserBuildings(@PathVariable("name") String username){
        return elevatorService.getUserBuildings(username);

    }
    @GetMapping("/user/{name}/{id}")
    public ResponseEntity<BuildingElevator> getBuildingElevators(@PathVariable("name") String username, @PathVariable("id") long id){
        return elevatorService.getBuildingElevatorsInformation(username,id);

    }

    @GetMapping("/buildings")
    public ResponseEntity<Map<String, List<BuildingElevator>>> getAllBuildingElevator(){
        return elevatorService.getAllBuildingElevator();
    }

}