package pl.elevator.elevator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.elevator.elevator.DTO.BuildingDataDTO;
import pl.elevator.elevator.models.Building;
import pl.elevator.elevator.models.Elevator;
import pl.elevator.elevator.services.ElevatorService;

import java.security.Principal;

@RestController
@RequestMapping(path = "/building")
@CrossOrigin
public class BuildingController {

    private ElevatorService elevatorService;
    @Autowired
    public BuildingController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }
    @PostMapping("/add")
    public ResponseEntity<Building> addNewBuilding(@RequestBody Building building, Principal principal){
        return elevatorService.addNewBuilding(building,principal);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBuilding(@PathVariable long id, Principal user){
        return elevatorService.deleteBuilding(id,user);
    }
    @DeleteMapping("/delete/elevator")
    public ResponseEntity deleteElevator(@RequestBody BuildingDataDTO buildingDataDTO,Principal principal){
        return elevatorService.deleteElevator(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),buildingDataDTO.getElevatorId(),principal);
    }
    @PostMapping("/add/elevator")
    public ResponseEntity addElevator(@RequestBody BuildingDataDTO buildingDataDTO,Principal principal){
        return elevatorService.addElevator(buildingDataDTO.getUsername(),buildingDataDTO.getBuildingId(),principal);
    }

    @GetMapping("/isauth")
    public ResponseEntity test(){
        return new ResponseEntity(HttpStatus.OK);
    }

}
