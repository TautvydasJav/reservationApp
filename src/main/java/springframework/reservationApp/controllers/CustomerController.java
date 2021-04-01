package springframework.reservationApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.repositories.SpecialistRepository;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;

    public CustomerController(CustomerRepository customerRepository, SpecialistRepository specialistRepository) {
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
    }

    @PostMapping("/customersList/addformsave")
    public String getPerson(Model model, Specialist specialist) {
        System.out.println("added id: "+specialist.getId());
        System.out.println("added time: "+specialist.getUsername());

        model.addAttribute("specialists", specialistRepository.findAll());
        return "specialistReservation";
    }

    @PostMapping("/customersAdd")
    public String addCustomers(@ModelAttribute("customer")Customer customer){

        //model.addAttribute("customers", customerRepository.findAll());
        System.out.println(customer.toString());
        return "specialistReservation";
    }


}
