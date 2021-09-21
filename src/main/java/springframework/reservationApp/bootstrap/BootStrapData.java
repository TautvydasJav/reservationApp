package springframework.reservationApp.bootstrap;


import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.reservationApp.services.RoleService;
import springframework.reservationApp.services.SpecialistService;
import springframework.reservationApp.services.UserService;

@Component
@AllArgsConstructor
public class BootStrapData implements CommandLineRunner {

    SpecialistService specialistService;
    UserService userService;
    RoleService roleService;

    @Override
    public void run(String... args) {
        roleService.createRole("SPECIALIST");
        roleService.createRole("DEPARTMENT");
        specialistService.addSpecialist("specialist1", "123", "SPECIALIST");
        specialistService.addSpecialist("specialist2", "123", "SPECIALIST");
        specialistService.addSpecialist("specialist3", "123", "SPECIALIST");
        userService.addUser("dept", "123", "DEPARTMENT");
    }
}
