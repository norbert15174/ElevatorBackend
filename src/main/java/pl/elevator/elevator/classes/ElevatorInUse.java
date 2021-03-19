package pl.elevator.elevator.classes;

import lombok.ToString;
import pl.elevator.elevator.models.Elevator;

import java.util.*;
import java.util.stream.Collectors;


public class ElevatorInUse extends Elevator {

    private Direction direction = Direction.NOT_SPECIFIED;
    private List<Integer> nextFlats = new ArrayList<>();

    public ElevatorInUse(Elevator elevator) {
        super(elevator.getId(),elevator.getCurrentFlat());
    }

    public boolean addNextFlat(int id){
        if(nextFlats.stream().filter(flat -> flat == id).findFirst().isPresent()){
            if(direction == Direction.NOT_SPECIFIED) moveToNextFlat();
            return false;
        }
        nextFlats.add(id);
        if(direction == Direction.NOT_SPECIFIED) moveToNextFlat();
        return true;
    }
    public List<Integer> getNextFlats(){
        if(nextFlats.size() != 0){
            List<Integer> flatsUp = nextFlats.stream().filter(flat -> flat > currentFlat).sorted().collect(Collectors.toList());
            List<Integer> flatsDown = nextFlats.stream().filter(flat -> flat < currentFlat).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            List<Integer> flatsInQueue = new ArrayList<>();
            switch (direction){
                case UP:
                    flatsUp.forEach(flat -> flatsInQueue.add(flat));
                    flatsDown.forEach(flat -> flatsInQueue.add(flat));
                    return flatsInQueue;
                case DOWN:
                    flatsDown.forEach(flat -> flatsInQueue.add(flat));
                    flatsUp.forEach(flat -> flatsInQueue.add(flat));
                    return flatsInQueue;
                default:
                    moveToNextFlat();
                    break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ElevatorInUse{" +
                "direction=" + direction +
                ", nextFlats=" + nextFlats +
                ", id=" + id +
                ", currentFlat=" + currentFlat +
                '}';
    }

    public boolean moveToNextFlat(){
        if(nextFlats.size() == 0){
            this.direction = Direction.NOT_SPECIFIED;
        }else{
            if(direction == Direction.UP){
                Integer setDirectionUp = Collections.max(nextFlats);
                if(setDirectionUp <= currentFlat) this.direction = Direction.DOWN;
            }else if(direction == Direction.DOWN){
                Integer setDirectionDown = Collections.min(nextFlats);
                if(setDirectionDown >= currentFlat) this.direction = Direction.UP;
            }
            Optional<Integer> deleteFlat = nextFlats.stream().filter(flat -> flat == currentFlat).findFirst();
            deleteFlat.ifPresent(flat -> nextFlats.remove(flat));
            if(nextFlats.size() == 0) return true;
        }

        switch (this.direction){
            case NOT_SPECIFIED:
                this.direction = Direction.UP;
                return false;
            case UP:
                currentFlat++;
                break;
            case DOWN:
                currentFlat--;
                break;
        }
        this.nextFlats = getNextFlats();
        return true;
    }



}
