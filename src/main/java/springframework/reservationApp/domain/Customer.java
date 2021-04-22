package springframework.reservationApp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import static springframework.reservationApp.enums.CustomerStatus.*;
import static springframework.reservationApp.utils.TimeUtils.*;

@Entity
@Access(AccessType.FIELD )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Comparable<Customer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String personalCode;

    private LocalTime visitTime;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    @ManyToOne()
    private Specialist specialist;

    @Override
    public int compareTo(Customer o) {
        return visitTime.compareTo(o.visitTime);
    }

    public Customer(String personalCode, LocalTime visitTime, Specialist specialist) {
        this.personalCode = personalCode;
        this.visitTime = visitTime;
        this.status = WAITING.name();
        this.specialist = specialist;
    }

    public void setAsCanceled() { status = CANCELED.name();}
    public void setAsDone() {
        status = DONE.name();
        endTime = getLocalTimeNow();
    }
    public void setAsInVisit() {
        status = INVISIT.name();
        startTime = getLocalTimeNow();
    }
}
