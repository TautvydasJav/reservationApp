package springframework.reservationApp.utils;

import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Iterator;

@Component
public class TimeUtils {
    private final int SESION_TIME_IN_MINUTES = 30; //  24h/0.5h max 48visits booked at one time

    public LocalTime getNextCustomersTime(Specialist specialist){
        if(specialist.getCustomers().isEmpty())
            return LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        Collections.sort(specialist.getCustomers());
        LocalTime earliestTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES).plus(SESION_TIME_IN_MINUTES, ChronoUnit.MINUTES);


       boolean emptySpaceFound = false;

        for(Customer customer : specialist.getCustomers()){
            if(emptySpaceFound){
                if(doesVisitTimeFitsBoundary(earliestTime, customer.getLocalTime())) {
                    return earliestTime;
                }
                else{
                    emptySpaceFound = false;
                    earliestTime = earliestTime.plus(SESION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
                }
            }

            if(!earliestTime.isBefore(customer.getLocalTime()))            // searches for gap in visit schedule
                earliestTime = customer.getLocalTime().plus(SESION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
            else
                emptySpaceFound = true;
        }
        return earliestTime;
    }

    public boolean doesVisitTimeFitsBoundary(LocalTime earliestTime, LocalTime nextVisit){

        LocalTime visitTimeFarthestBoundary = earliestTime.plus(SESION_TIME_IN_MINUTES, ChronoUnit.MINUTES);

        if(visitTimeFarthestBoundary.isBefore(nextVisit)
                || visitTimeFarthestBoundary.equals(nextVisit))
            return true;
        else
            return false;
    }

    public int getMaxVisitsCount(){
        return 24*60/SESION_TIME_IN_MINUTES;
    }
}
