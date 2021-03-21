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
        return elevatorService.getNextFloor(username, buildingId, elevatorId);
    }

    @PostMapping("/add/{floor}")
    public ResponseEntity<BuildingElevator> addFlats(@RequestBody BuildingDataDTO buildingDataDTO, @PathVariable("floor") int floor) {
        return elevatorService.addNewFloor(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),buildingDataDTO.getElevatorId(),floor);
    }
    @PostMapping("/move")
    public ResponseEntity<List<Integer>> moveElevator(@RequestBody BuildingDataDTO buildingDataDTO) {
        return elevatorService.moveElevator(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),buildingDataDTO.getElevatorId());
    }
    @GetMapping("/floor")
    public ResponseEntity<Integer> getCurrentFloor(@RequestParam String username, @RequestParam long buildingId, @RequestParam long elevatorId) {
        return elevatorService.getCurrentFloor(username,buildingId,elevatorId);
    }
    @GetMapping("/user/{name}")
    public ResponseEntity<List<BuildingElevator>> getUserBuildings(@PathVariable("name") String username){
        return elevatorService.getUserBuildings(username);

    }
    @GetMapping("/user/{name}/{id}")
    public ResponseEntity<BuildingElevator> getBuildingElevators(@PathVariable("name") String username, @PathVariable("id") long id){
        return elevatorService.getBuildingElevatorsInformation(username,id);

    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }


    @GetMapping("/buildings")
    public ResponseEntity<Map<String, List<BuildingElevator>>> getAllBuildingElevator(){
        return elevatorService.getAllBuildingElevator();
    }

}