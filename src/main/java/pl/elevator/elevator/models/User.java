package pl.elevator.elevator.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private Role role = Role.ROLE_ADMIN;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Building> buildings = new ArrayList<>();

    public boolean addBuilding(Building building){
       if(buildings.stream().filter(b -> b.getBuildingName() == building.getBuildingName()).findFirst().isPresent()) return false ;
        buildings.add(building);
        return true;
    }
    public boolean deleteBuilding(String name){
        Optional<Building> buildingToDelete = buildings.stream().filter(b -> b.getBuildingName() == name).findFirst();
        if(!buildingToDelete.isPresent()) return false ;
        buildings.remove(buildingToDelete.get());
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
