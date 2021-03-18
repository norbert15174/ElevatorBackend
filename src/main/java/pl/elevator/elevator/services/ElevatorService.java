package pl.elevator.elevator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pl.elevator.elevator.classes.BuildingElevator;

import pl.elevator.elevator.classes.ElevatorInUse;
import pl.elevator.elevator.models.Building;
import pl.elevator.elevator.models.Elevator;
import pl.elevator.elevator.models.User;
import pl.elevator.elevator.repositories.BuildingRepository;
import pl.elevator.elevator.repositories.ElevatorRepository;
import pl.elevator.elevator.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ElevatorService {


    private Map<String, List<BuildingElevator>> buildingElevator = new HashMap<>();
    private UserRepository userRepository;
    private BuildingRepository buildingRepository;
    private ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorService(UserRepository userRepository, BuildingRepository buildingRepository,ElevatorRepository elevatorRepository) {
            this.userRepository = userRepository;
            this.buildingRepository = buildingRepository;
            this.elevatorRepository = elevatorRepository;
            try {
                List<User> user = userRepository.getAllUserBuildings().get();
                for(User u : user){
                    List<BuildingElevator> userBuildingsToSave = new ArrayList<>();
                    u.getBuildings().forEach(building -> userBuildingsToSave.add(new BuildingElevator(building)));
                    this.buildingElevator.put(u.getUsername(),userBuildingsToSave);
                };
            }catch (NullPointerException e){
                System.err.println("Data in DB doesn't exist");
            }
    }

    private int getBuildingId(String username, long buildingId){
        try {
            int id = buildingElevator.get(username).indexOf(buildingElevator.get(username).stream().filter(building -> building.getId() == buildingId).findFirst().get());
            return id;
        }catch (NullPointerException e){
            System.err.println("Building doesn't exist");
            return -1;
        }

    }


    public ResponseEntity addNewFlat(String username, long buildingId, long elevatorId, int flat){
        int id = getBuildingId(username,buildingId);
        if(id == -1 || buildingElevator.get(username).get(id).getElevatorInUseList().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).setLastUsed(LocalDateTime.now());
        buildingElevator.get(username).get(id).getElevatorInUseList().get(elevatorId).addNextFlat(flat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<List<Integer>> getNextFlats(String username, long buildingId, long elevatorId){
        int id = getBuildingId(username,buildingId);
        if(id == -1 || buildingElevator.get(username).get(id).getElevatorInUseList().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).setLastUsed(LocalDateTime.now());
        List<Integer> flats = buildingElevator.get(username).get(id).getElevatorInUseList().get(elevatorId).getNextFlats();
        return new ResponseEntity<>(flats,HttpStatus.OK);
    }
    public ResponseEntity<List<Integer>> moveElevator(String username, long buildingId, long elevatorId){
        int id = getBuildingId(username,buildingId);
        if(id == -1 || buildingElevator.get(username).get(id).getElevatorInUseList().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).setLastUsed(LocalDateTime.now());
        buildingElevator.get(username).get(id).getElevatorInUseList().get(elevatorId).moveToNextFlat();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<Integer> getCurrentFlat(String username, long buildingId, long elevatorId){
        int id = getBuildingId(username,buildingId);
        if(id == -1 || buildingElevator.get(username).get(id).getElevatorInUseList().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).setLastUsed(LocalDateTime.now());
        int flat = buildingElevator.get(username).get(id).getElevatorInUseList().get(elevatorId).getCurrentFlat();
        return new ResponseEntity<>(flat,HttpStatus.OK);
    }
    public ResponseEntity<List<BuildingElevator>> getUserBuildings(String username){
        return new ResponseEntity<>(buildingElevator.get(username),HttpStatus.OK);
    }
    public ResponseEntity<BuildingElevator> getBuildingElevatorsInformation(String username, long buildingId){
        int id = getBuildingId(username,buildingId);
        if(id == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).setLastUsed(LocalDateTime.now());
        BuildingElevator building = buildingElevator.get(username).get(id);
        return new ResponseEntity<>(building,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Building> addNewBuilding(Building building, UserDetails userDetails){
        if(buildingRepository.findBuildingByName(building.getBuildingName()).isPresent()) return new ResponseEntity<>(HttpStatus.CONFLICT);
        building.setLastUsed(LocalDateTime.now());
        Optional<User> user = userRepository.getUserBuildings(userDetails.getUsername());
        if(user.isPresent()){
            User getUser = user.get();
            if(getUser.addBuilding(building)){
                userRepository.save(getUser);
                buildingElevator.get(userDetails.getUsername()).add(new BuildingElevator(buildingRepository.findBuildingByName(building.getBuildingName()).get()));
                return new ResponseEntity<>(building,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Transactional
    public ResponseEntity deleteBuilding(long id, UserDetails userDetails){
        Optional<User> user = userRepository.getUserBuildings(userDetails.getUsername());
        if(user.isPresent()){
            User getUser = user.get();
            if(getUser.getBuildings().stream().filter(b -> b.getId() == id).findFirst().isPresent()){
                buildingRepository.deleteById(id);
                int buildingId = getBuildingId(userDetails.getUsername(),id);
                buildingElevator.get(userDetails.getUsername()).remove(buildingId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Transactional
    public ResponseEntity deleteElevator(String username, long buildingId, long elevatorId, UserDetails userDetails){
        int id = getBuildingId(username,buildingId);
        if(id == -1 || buildingElevator.get(username).get(id).getElevatorInUseList().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        buildingElevator.get(username).get(id).getElevatorInUseList().remove(elevatorId);
        elevatorRepository.deleteById(elevatorId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity addElevator(String username, long buildingId, Elevator elevator, UserDetails userDetails){
        Optional<Building> building = buildingRepository.findBuildingById(buildingId);
        if(!building.isPresent()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        Building buildingToSave = building.get();
        int id = getBuildingId(username,buildingId);
        if(id == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Elevator getElevator = elevatorRepository.save(elevator);
        buildingToSave.getElevators().add(elevator);
        buildingRepository.save(buildingToSave);
        buildingElevator.get(username).get(id).getElevatorInUseList().put(getElevator.getId(),new ElevatorInUse(getElevator));
        return new ResponseEntity(HttpStatus.OK);
    }





    @EventListener(ApplicationReadyEvent.class)
    public void test(){
//        User user = new User();
//        user.setUsername("Norbert1");
//        user.setPassword("N@jlepszy");
//       Building building = new Building();
//        building.setBuildingName("asd");
//        building.setFloorsNumber(20);
//        building.setLastUsed(LocalDateTime.now());
//       Elevator elevator = new Elevator();
//        List<Elevator> elevators = new ArrayList<>();
//        elevator.setCurrentFlat(5);
//        elevators.add(elevator);
//        Elevator elevator1 = new Elevator();
//        elevator1.setCurrentFlat(8);
//        elevators.add(elevator1);
//       building.setElevators(elevators);
//        List<Building> buildings = new ArrayList<>();
//        buildings.add(building);
//   //     buildings.add(building);
//
//        user.setBuildings(buildings);
//        userRepository.save(user);
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).addNextFlat(2));
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).addNextFlat(5));
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).moveToNextFlat());
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).moveToNextFlat());
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).moveToNextFlat());
//        System.out.println(buildingElevator.get("Norbert").get(0).getElevatorInUseList().get(2l).getCurrentFlat());
 //       System.out.println(buildingElevator.get("Norbert").get(1).getElevatorInUseList().values());
    }



}
