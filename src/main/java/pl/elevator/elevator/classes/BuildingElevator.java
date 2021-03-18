package pl.elevator.elevator.classes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.elevator.elevator.models.Building;
import pl.elevator.elevator.models.Elevator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@ToString
public class BuildingElevator {
    private long id;
    private int floorsNumber;
    private String buildingName;
    private LocalDateTime lastUsed;

    private Map<Long,ElevatorInUse> elevatorInUseList = new HashMap<>();

    public BuildingElevator(Building building){
        this.id = building.getId();
        this.floorsNumber = building.getFloorsNumber();
        this.buildingName = building.getBuildingName();
        this.lastUsed = LocalDateTime.now();
        for(Elevator elevator : building.getElevators()){
            elevatorInUseList.put(elevator.getId(),new ElevatorInUse(elevator));
        }
    }





}
