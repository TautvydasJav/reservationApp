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
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.SpecialistRepository;
import springframework.reservationApp.services.CustomerService;
import springframework.reservationApp.services.SpecialistService;

@Controller
@AllArgsConstructor
public class SpecialistScreenController {

    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final SpecialistRepository specialistRepository;

    @RequestMapping("/specialist")
    public String getSpecialistScreen(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Specialist currentSpecialist = specialistRepository.findByUsername(auth.getName());

        model.addAttribute("customers", customerService.getAvailable(currentSpecialist));
        model.addAttribute("status", specialistService.isInVisit(currentSpecialist));
        return "specialistScreen";
    }

    @RequestMapping("/specialist/start")
    public String startVisit(@ModelAttribute("type") String type, RedirectAttributes redirectAttributes){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Specialist currentSpecialist = specialistRepository.findByUsername(auth.getName());

        if(specialistService.checkForAvailableVisit(customerService.getAvailable(currentSpecialist))) {
            specialistService.setStartOfVisit(currentSpecialist);
        }
        else{
            redirectAttributes.addFlashAttribute("error", "No visits for now");
        }

        return "redirect:/specialist";
    }

    @RequestMapping("/specialist/end")
    public String endVisit(@ModelAttribute("type") String type){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Specialist currentSpecialist = specialistRepository.findByUsername(auth.getName());

        if(type.equals("END")){
            specialistService.setEndOfVisit(currentSpecialist);
        }
        return "redirect:/specialist";
    }

    @RequestMapping(value = "/specialist/delete", method = RequestMethod.GET)
    public String deleteCustomerFromSpecialistScreen(@ModelAttribute("id") String id){

        customerService.setAsCanceled(Integer.parseInt(id));
        return "redirect:/specialist";
    }
}
