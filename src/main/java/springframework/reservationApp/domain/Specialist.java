package springframework.reservationApp.domain;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Customer> customers;

    @OneToOne
    private Customer customerInVisit;

    public Specialist(String username, String password, HashSet<Role> roles, boolean active) {
        super(username, password, roles, active);
    }
}
