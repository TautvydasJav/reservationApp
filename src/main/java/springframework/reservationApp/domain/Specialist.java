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

    protected boolean inVisit;

    @OneToMany(mappedBy = "specialist", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Customer> customers;

    public Specialist(String username, String password) {
        this.username = username;
        this.password = password;
        inVisit = false;
    }

    public void removeFirstCustomer(){
        getCustomers().remove(getCustomers().get(0));
    }
}
