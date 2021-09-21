package springframework.reservationApp.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    @OneToOne
    private Reservation reservationInVisit;

    public Specialist(String username, String password, boolean active, Set<Role> roles) {
        super(username, password, active, roles);
    }
}
