//package pl.elevator.elevator.mappers;
//
//import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.springframework.stereotype.Component;
//import pl.elevator.elevator.DTO.BuildingElevatorDTO;
//import pl.elevator.elevator.DTO.ElevatorInUseDTO;
//import pl.elevator.elevator.classes.BuildingElevator;
//import pl.elevator.elevator.classes.ElevatorInUse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@NoArgsConstructor
//public class ElevatorInUseMapper {
//
//
//
//    private static ModelMapper elevatorObjectMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new PropertyMap<ElevatorInUse, ElevatorInUseDTO>() {
//            @Override
//            protected void configure() {
//                map().setCurrentFlat(source.getCurrentFlat());
//                map().setFlatsQueue(source.getNextFlats());
//                map().setId(source.getId());
//            }
//        });
//        return modelMapper;
//    }
//
//    //Return mapped models
//    public static List<ElevatorInUseDTO> mapElevatorInUseToElevatorInUseDTO(List<ElevatorInUse> elevatorInUse) {
//        List<ElevatorInUseDTO> elevatorInUseDTOS = new ArrayList<>();
//        elevatorInUse.forEach((pd -> elevatorInUseDTOS.add(elevatorObjectMapper().map(pd, ElevatorInUseDTO.class))));
//        return elevatorInUseDTOS;
//    }
//
//    //Return mapped model
//    public static ElevatorInUseDTO mapElevatorInUseToElevatorInUseDTO(ElevatorInUse elevatorInUse) {
//        ElevatorInUseDTO elevatorInUseDTO;
//        elevatorInUseDTO = elevatorObjectMapper().map(elevatorInUse, ElevatorInUseDTO.class);
//        return elevatorInUseDTO;
//    }
//
//
//}
