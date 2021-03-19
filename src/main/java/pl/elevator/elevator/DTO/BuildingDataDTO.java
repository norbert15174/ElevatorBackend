package pl.elevator.elevator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDataDTO {
    private String username;
    private long buildingId;
    private long elevatorId;
}
