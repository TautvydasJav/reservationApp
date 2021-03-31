package springframework.reservationApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.repositories.SpecialistRepository;

import java.time.temporal.ChronoUnit;


@Controller
public class SpecialistController {

    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;

    public SpecialistController(CustomerRepository customerRepository, SpecialistRepository specialistRepository) {
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
    }

    @RequestMapping("/reservation")
    public String getSpecialists(Model model){

        model.addAttribute("specialists", specialistRepository.findAll());

        return "customer/specialistReservation";
    }

    @PostMapping("/reservation")
    public String postCustomer(Model model, Specialist specialist, RedirectAttributes redirectAttributes) {
        System.out.println("added id: "+specialist.getId());
        System.out.println("added time: "+specialist.getName());

        model.addAttribute("specialists", specialistRepository.findAll());
        redirectAttributes.addFlashAttribute("message", "Failed");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if (specialist.hasErrors()) {
            return "redirect:/reservation";
        }

        Customer newCustomer = new Customer();
        newCustomer.setSpecialist(specialistRepository.findById(specialist.getId()));
        newCustomer.setLocalTime(specialistRepository.findById(specialist.getId()).getNextCustomersTime());
        customerRepository.save(newCustomer);

        redirectAttributes.addFlashAttribute("message", "Success");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("id", newCustomer.getId());
        redirectAttributes.addFlashAttribute("time", newCustomer.getLocalTime());
        return "redirect:/reservation";
    }

    @RequestMapping("/specialistList")
    public String getCustomers(Model model){

        model.addAttribute("specialists", specialistRepository.findAll());

        return "customer/list";
    }

    /*
    @PostMapping("/customersAdd")
    public String addCustomers(@ModelAttribute("specialist") Specialist specialist){

        //model.addAttribute("customers", customerRepository.findAll());
        System.out.println(specialist.toString());
        return "customer/add";
    }

     */
}