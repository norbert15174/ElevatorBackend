//package pl.elevator.elevator.mappers;
//
//import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.springframework.stereotype.Component;
//import pl.elevator.elevator.DTO.BuildingElevatorDTO;
//import pl.elevator.elevator.classes.BuildingElevator;
//import pl.elevator.elevator.classes.ElevatorInUse;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//@Component
//@NoArgsConstructor
//public class BuildingElevatorMapper {
//
//
//
//    private static ModelMapper elevatorObjectMapper() {
//
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new PropertyMap<BuildingElevator, BuildingElevatorDTO>() {
//
//            @Override
//            protected void configure() {
//                map().setBuildingName(source.getBuildingName());
//                map().setFloorsNumber(source.getFloorsNumber());
//                map().setId(source.getId());
//        //        map().setElevators(ElevatorInUseMapper.mapElevatorInUseToElevatorInUseDTO(new ArrayList<>(source.getElevatorInUseList().values())));
//            }
//        });
//        return modelMapper;
//    }
//
//    //Return mapped models
//    public static List<BuildingElevatorDTO> mapBuildingElevatorToBuildingElevatorDTO(List<BuildingElevator> buildingElevators) {
//        List<BuildingElevatorDTO> buildingElevatorDTO = new ArrayList<>();
//        buildingElevators.forEach((pd -> buildingElevatorDTO.add(elevatorObjectMapper().map(pd, BuildingElevatorDTO.class))));
//        return buildingElevatorDTO;
//    }
//
//    //Return mapped model
//    public static BuildingElevatorDTO mapBuildingElevatorToBuildingElevatorDTO(BuildingElevator buildingElevator) {
//        BuildingElevatorDTO buildingElevatorDTO;
//        buildingElevatorDTO = elevatorObjectMapper().map(buildingElevator, BuildingElevatorDTO.class);
//        return buildingElevatorDTO;
//    }
//
//
//}
