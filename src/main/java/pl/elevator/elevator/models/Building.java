package pl.elevator.elevator.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private int floorsNumber;
    @NotNull
    private String buildingName;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Elevator> elevators = new ArrayList<>();
    private LocalDateTime lastUsed;


}
