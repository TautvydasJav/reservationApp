package springframework.reservationApp.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Access( AccessType.FIELD )
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(targetEntity=Customer.class, mappedBy="specialist", fetch=FetchType.EAGER)
    private List<Customer> customers = new ArrayList<>();

    public Specialist() {}

    public Specialist(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialist that = (Specialist) o;
        return Objects.equals(id, that.id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public boolean hasErrors(){
        if(this.id == 0)
            return true;
        else return false;
    }

    public LocalTime getNextCustomersTime(){
        if(customers.isEmpty())
            return LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        Collections.sort(customers);
        LocalTime earliestTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        for(Customer customer : customers){
            if(customer.getLocalTime().truncatedTo(ChronoUnit.MINUTES).equals(earliestTime))
                earliestTime = customer.getLocalTime().plus(5, ChronoUnit.MINUTES);
            else
                return earliestTime;
        }
        return earliestTime;
    }
}
