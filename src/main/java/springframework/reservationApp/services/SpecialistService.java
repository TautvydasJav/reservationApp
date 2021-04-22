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
    private TimeUtils timeUtils;

    public void setStartOfVisit(Specialist specialist){
        specialist.setCustomerInVisit(specialist.getCustomers().get(0));
        specialist.getCustomerInVisit().setAsInVisit();
        specialistRepository.save(specialist);
    }

    public void setEndOfVisit(Specialist specialist){
        specialist.getCustomerInVisit().setAsDone();
        specialist.setCustomerInVisit(null);
        specialistRepository.save(specialist);
    }

    public boolean isInVisit(Specialist specialist){
        Optional<Customer> customer= Optional.ofNullable(specialist.getCustomerInVisit());
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

    public boolean checkForAvailableVisit(List<Customer> customers){

        if(customers.isEmpty())
            return false;

        if(customers.get(0).getVisitTime().isBefore(timeUtils.getLocalTimeNow()) ||
                customers.get(0).getVisitTime().equals(timeUtils.getLocalTimeNow())){
            return true;
        }
        else
            return false;
    }
}
