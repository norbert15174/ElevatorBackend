package pl.elevator.elevator.classes;

import pl.elevator.elevator.models.Elevator;

import java.util.*;
import java.util.stream.Collectors;


public class ElevatorInUse extends Elevator {

    private Direction direction = Direction.NOT_SPECIFIED;
    private List<Integer> nextFloors = new ArrayList<>();

    public ElevatorInUse(Elevator elevator) {
        super(elevator.getId(),elevator.getCurrentFloor());
    }

    public boolean addNextFloor(int id){
        if(nextFloors.stream().filter(floor -> floor == id).findFirst().isPresent()){
            if(direction == Direction.NOT_SPECIFIED) moveToNextFloor();
            return false;
        }
        nextFloors.add(id);
        if(direction == Direction.NOT_SPECIFIED) moveToNextFloor();
        return true;
    }
    public List<Integer> getNextFloors(){
        if(nextFloors.size() != 0){
            List<Integer> floorsUp = nextFloors.stream().filter(floor -> floor > currentFloor).sorted().collect(Collectors.toList());
            List<Integer> floorsDown = nextFloors.stream().filter(floor -> floor < currentFloor).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            List<Integer> flatsInQueue = new ArrayList<>();
            switch (direction){
                case UP:
                    floorsUp.forEach(floor -> flatsInQueue.add(floor));
                    floorsDown.forEach(floor -> flatsInQueue.add(floor));
                    return flatsInQueue;
                case DOWN:
                    floorsDown.forEach(floor -> flatsInQueue.add(floor));
                    floorsUp.forEach(floor -> flatsInQueue.add(floor));
                    return flatsInQueue;
                default:
                    moveToNextFloor();
                    break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ElevatorInUse{" +
                "direction=" + direction +
                ", nextFloors=" + nextFloors +
                ", id=" + id +
                ", currentFloor=" + currentFloor +
                '}';
    }

    public boolean moveToNextFloor(){
        if(nextFloors.size() == 0){
            this.direction = Direction.NOT_SPECIFIED;
        }else{
            if(direction == Direction.UP){
                Integer setDirectionUp = Collections.max(nextFloors);
                if(setDirectionUp <= currentFloor) this.direction = Direction.DOWN;
            }else if(direction == Direction.DOWN){
                Integer setDirectionDown = Collections.min(nextFloors);
                if(setDirectionDown >= currentFloor) this.direction = Direction.UP;
            }
            Optional<Integer> deleteFloor = nextFloors.stream().filter(flat -> flat == currentFloor).findFirst();
            deleteFloor.ifPresent(floor -> nextFloors.remove(floor));
            if(nextFloors.size() == 0) return true;
        }

        switch (this.direction){
            case NOT_SPECIFIED:
                this.direction = Direction.UP;
                return false;
            case UP:
                currentFloor++;
                break;
            case DOWN:
                currentFloor--;
                break;
        }
        this.nextFloors = getNextFloors();
        return true;
    }



}
