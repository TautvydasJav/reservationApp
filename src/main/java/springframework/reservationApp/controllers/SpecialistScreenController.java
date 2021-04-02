package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.services.CustomerService;
import springframework.reservationApp.services.SpecialistService;

import java.util.List;

@Controller
@AllArgsConstructor
public class SpecialistScreenController {

    private final SpecialistService specialistService;
    private final CustomerService customerService;

    @RequestMapping("/specialist")
    public String getSpecialistScreen(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Specialist currentSpecialist = specialistService.findSpecialistByUsername(auth.getName());
        List<Customer> customers = specialistService.getCustomersFromSpecialistByUsername(auth.getName());

        model.addAttribute("customers", customers);
        model.addAttribute("status", currentSpecialist.isInVisit());
        return "specialistScreen";
    }

    @RequestMapping("/specialist/visit-status")
    public String visitStatus(Model model, @ModelAttribute("type") String type, RedirectAttributes redirectAttributes){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Specialist currentSpecialist = specialistService.findSpecialistByUsername(auth.getName());

        if(type.equals("START")){
            if(specialistService.checkForAvailableVisit(currentSpecialist)) {
                specialistService.setIsInVisitByUsername(auth.getName(), true);
            }
            else{
                redirectAttributes.addFlashAttribute("error", "No visits for now");
            }
        }
        if(type.equals("END")){
            specialistService.setIsNotInVisitByUsername(auth.getName(), false);
        }

        List<Customer> customers = currentSpecialist.getCustomers();
        model.addAttribute("customers", customers);
        redirectAttributes.addFlashAttribute("status", currentSpecialist.isInVisit());

        return "redirect:/specialist";
    }

    @RequestMapping(value = "/specialist/delete", method = RequestMethod.GET)
    public String deleteCustomerFromSpecialistScreen(Model model, @ModelAttribute("id") String id) {

        customerService.deleteCustomerById(Integer.parseInt(id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Customer> customers = specialistService.getCustomersFromSpecialistByUsername(auth.getName());
        model.addAttribute("customers", customers);
        return "redirect:/specialist";
    }
}
