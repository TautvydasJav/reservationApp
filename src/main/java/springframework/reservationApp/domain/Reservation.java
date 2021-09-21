package springframework.reservationApp.domain;

import lombok.*;
import springframework.reservationApp.utils.TimeUtils;

import javax.persistence.*;
import java.time.LocalTime;
import static springframework.reservationApp.enums.CustomerStatus.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation implements Comparable<Reservation>{

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
    public int compareTo(Reservation o) {
        return visitTime.compareTo(o.visitTime);
    }

    public Reservation(String personalCode, LocalTime visitTime, Specialist specialist) {
        this.personalCode = personalCode;
        this.visitTime = visitTime;
        this.status = WAITING.name();
        this.specialist = specialist;
    }

    public void setAsCanceled() { status = CANCELED.name();}

    public void setAsDone() {
        status = DONE.name();
        endTime = TimeUtils.getLocalTimeNow();
    }
    public void setAsInVisit() {
        status = INVISIT.name();
        startTime = TimeUtils.getLocalTimeNow();
    }

    public boolean isActive() {
        if(status.equals(WAITING.name())){
            return true;
        } else {
            return false;
        }
    }
}
