package springframework.reservationApp.utils;

import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Component
public class TimeUtils {

    private static final int SESSION_TIME_IN_MINUTES = 30; //  24h/0.5h max 48visits booked at one time
    private static final ZoneId localZone = ZoneId.of("Europe/Vilnius");

    public static LocalTime getNextCustomersTime(List<Customer> customers){
        if(customers.isEmpty())
            return getLocalTimeNow();

        Collections.sort(customers);
        LocalTime earliestTime = getLocalTimeNow();


       boolean emptySpaceFound = false;

        for(Customer customer : customers){
            if(emptySpaceFound){
                if(doesVisitTimeFitsBoundary(earliestTime, customer.getVisitTime())) {
                    return earliestTime;
                }
                else{
                    emptySpaceFound = false;
                    earliestTime = earliestTime.plus(SESSION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
                }
            }

            if(!earliestTime.isBefore(customer.getVisitTime()))            // searches for gap in visit schedule
                earliestTime = customer.getVisitTime().plus(SESSION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
            else{
                if(doesVisitTimeFitsBoundary(earliestTime, customer.getVisitTime())) {
                    return earliestTime;
                }
                emptySpaceFound = true;
            }
        }
        return earliestTime;
    }

    public static boolean doesVisitTimeFitsBoundary(LocalTime earliestTime, LocalTime nextVisit){

        LocalTime visitTimeFarthestBoundary = earliestTime.plus(SESSION_TIME_IN_MINUTES, ChronoUnit.MINUTES);

        if(visitTimeFarthestBoundary.isBefore(nextVisit)
                || visitTimeFarthestBoundary.equals(nextVisit))
            return true;
        else
            return false;
    }

    public static LocalTime getLocalTimeNow(){
        return LocalTime.now(localZone).truncatedTo(ChronoUnit.MINUTES);
    }

    public int getMaxVisitsCount(){
        return 24*60/SESSION_TIME_IN_MINUTES;
    }
}
