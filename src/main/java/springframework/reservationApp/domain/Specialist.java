package springframework.reservationApp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    @OneToOne
    private Reservation reservationInVisit;

    public Specialist(String username, String password, HashSet<Role> roles, boolean active) {
        super(username, password, roles, active);
    }
}
