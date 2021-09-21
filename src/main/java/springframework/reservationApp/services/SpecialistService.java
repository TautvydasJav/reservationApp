package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Specialist getLoggedInSpecialist(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return specialistRepository.findByUsername(auth.getName());
    }

    public void setStartOfVisit(){
        Specialist specialist = getLoggedInSpecialist();
        specialist.setReservationInVisit(specialist.getReservations().get(0));
        specialist.getReservationInVisit().setAsInVisit();
        specialistRepository.save(specialist);
    }

    public void setEndOfVisit(){
        Specialist specialist = getLoggedInSpecialist();
        specialist.getReservationInVisit().setAsDone();
        specialist.setReservationInVisit(null);
        specialistRepository.save(specialist);
    }

    public boolean isInVisit(){
        Specialist specialist = getLoggedInSpecialist();
        Optional<Reservation> customer= Optional.ofNullable(specialist.getReservationInVisit());
        if(customer.isPresent())
            return true;
        else
            return false;
    }

    public Specialist addSpecialist(String username, String password, String role){
        return specialistRepository.save(Specialist.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .roles(new HashSet<>(Arrays.asList(roleService.findByRoleName(role))))
                .active(true)
                .build());
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
