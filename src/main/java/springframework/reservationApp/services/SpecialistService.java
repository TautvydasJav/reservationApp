package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.*;
import springframework.reservationApp.repositories.SpecialistRepository;
import springframework.reservationApp.utils.TimeUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpecialistService{

    private SpecialistRepository specialistRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setStartOfVisit(Specialist specialist){
        specialist.setReservationInVisit(specialist.getReservations().get(0));
        specialist.getReservationInVisit().setAsInVisit();
        specialistRepository.save(specialist);
    }

    public void setEndOfVisit(Specialist specialist){
        specialist.getReservationInVisit().setAsDone();
        specialist.setReservationInVisit(null);
        specialistRepository.save(specialist);
    }

    public boolean isInVisit(Specialist specialist){
        Optional<Reservation> customer= Optional.ofNullable(specialist.getReservationInVisit());
        if(customer.isPresent())
            return true;
        else
            return false;
    }

    public Specialist addSpecialist(String username, String password, String role){
        Specialist newSpecialist = new Specialist(username
                                                    , bCryptPasswordEncoder.encode(password)
                                                    , new HashSet<>(Arrays.asList(roleService.findByRoleName(role)))
                                                    ,true);
        return specialistRepository.save(newSpecialist);
    }

    public boolean checkForAvailableVisit(List<Reservation> reservations){
        if(reservations.isEmpty())
            return false;

        if(reservations.get(0).getVisitTime().isBefore(TimeUtils.getLocalTimeNow()) ||
                reservations.get(0).getVisitTime().equals(TimeUtils.getLocalTimeNow())){
            return true;
        }
        else
            return false;
    }
}
