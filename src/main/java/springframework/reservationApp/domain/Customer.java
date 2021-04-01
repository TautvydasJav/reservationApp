package springframework.reservationApp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Access( AccessType.FIELD )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Comparable<Customer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String personalCode;
    private LocalTime localTime;

    @ManyToOne
    private Specialist specialist;

    @Override
    public int compareTo(Customer o) {
        return localTime.compareTo(o.localTime);
    }
}
