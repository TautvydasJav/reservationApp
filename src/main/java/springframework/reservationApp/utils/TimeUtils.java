package springframework.reservationApp.utils;

import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Component
public class TimeUtils {
    public LocalTime getNextCustomersTime(Specialist specialist){
        if(specialist.getCustomers().isEmpty())
            return LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        Collections.sort(specialist.getCustomers());
        LocalTime earliestTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        for(Customer customer : specialist.getCustomers()){
            if(customer.getLocalTime().truncatedTo(ChronoUnit.MINUTES).equals(earliestTime))
                earliestTime = customer.getLocalTime().plus(5, ChronoUnit.MINUTES);
            else
                return earliestTime;
        }
        return earliestTime;
    }
}
