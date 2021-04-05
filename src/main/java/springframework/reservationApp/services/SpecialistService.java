package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.*;
import springframework.reservationApp.repositories.SpecialistRepository;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class SpecialistService{

    private SpecialistRepository specialistRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<Specialist> findAll() {
        return specialistRepository.findAll();
    }

    public Specialist findById(int id) {
        return specialistRepository.findById(id);
    }

    public Specialist findSpecialist(String username) {
        return specialistRepository.findByUsername(username);
    }

    public List<Customer> getCustomersFromSpecialist(String username){
        return specialistRepository.findByUsername(username).getCustomers();
    }

    public void setIsInVisit(String username, boolean state){
        Specialist specialist = specialistRepository.findByUsername(username);
        specialist.removeFirstCustomer();
        specialist.setInVisit(state);
        specialistRepository.save(specialist);
    }

    public void setIsNotInVisit(String username, boolean state){
        specialistRepository.findByUsername(username).setInVisit(state);
        specialistRepository.save(specialistRepository.findByUsername(username));
    }

    public Specialist addSpecialist(String username, String password, String role){
        Specialist newSpecialist = new Specialist();
        newSpecialist.setUsername(username);
        newSpecialist.setPassword(bCryptPasswordEncoder.encode(password));
        newSpecialist.setActive(true);
        Role userRole = roleService.findByRoleName(role);
        newSpecialist.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return specialistRepository.save(newSpecialist);
    }

    public boolean checkForAvailableVisit(Specialist specialist){
        LocalTime presentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        if(specialist.getCustomers().isEmpty())
            return false;
        List<Customer> customers = specialist.getCustomers();
        customers.get(0).getLocalTime();
        if(customers.get(0).getLocalTime().isBefore(presentTime) || customers.get(0).getLocalTime().equals(presentTime)){
            return true;
        }
        else
            return false;
    }
}
