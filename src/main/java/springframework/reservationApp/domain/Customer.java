package springframework.reservationApp.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Access( AccessType.FIELD )
public class Customer implements Comparable<Customer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String time;
    private LocalTime localTime;

    @ManyToOne
    private Specialist specialist;

    public Customer() {}

    public Customer(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public int compareTo(Customer o) {
        return localTime.compareTo(o.localTime);
    }
}
