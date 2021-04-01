package springframework.reservationApp.bootstrap;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.repositories.SpecialistRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;

    public BootStrapData(CustomerRepository customerRepository, SpecialistRepository specialistRepository) {
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
    }



    @Override
    public void run(String... args) throws Exception {

        Specialist specialist1 = new Specialist("specialist1", "123");
        Specialist specialist2 = new Specialist("specialist2", "123");

        specialistRepository.save(specialist1);
        specialistRepository.save(specialist2);


    }
}
