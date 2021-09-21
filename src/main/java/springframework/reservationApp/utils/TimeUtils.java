package springframework.reservationApp.utils;

import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Reservation;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Component
public class TimeUtils {

    private static final int SESSION_TIME_IN_MINUTES = 30; //  24h/0.5h max 48visits booked at one time
    private static final ZoneId localZone = ZoneId.of("Europe/Vilnius");

    public static LocalTime getNextCustomersTime(List<Reservation> reservations){
        if(reservations.isEmpty())
            return getLocalTimeNow();

        Collections.sort(reservations);
        LocalTime earliestTime = getLocalTimeNow();


       boolean emptySpaceFound = false;

        for(Reservation reservation : reservations){
            if(emptySpaceFound){
                if(doesVisitTimeFitsBoundary(earliestTime, reservation.getVisitTime())) {
                    return earliestTime;
                }
                else{
                    emptySpaceFound = false;
                    earliestTime = earliestTime.plus(SESSION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
                }
            }

            if(!earliestTime.isBefore(reservation.getVisitTime()))            // searches for gap in visit schedule
                earliestTime = reservation.getVisitTime().plus(SESSION_TIME_IN_MINUTES, ChronoUnit.MINUTES);
            else{
                if(doesVisitTimeFitsBoundary(earliestTime, reservation.getVisitTime())) {
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

    public static int getMaxVisitsCount(){
        return 24*60/SESSION_TIME_IN_MINUTES;
    }
}
