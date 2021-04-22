package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.repositories.SpecialistRepository;
import springframework.reservationApp.services.CustomerService;

@Controller
@AllArgsConstructor
public class ReservationScreenController {

    private final CustomerService customerService;
    private final SpecialistRepository specialistRepository;

    @RequestMapping("/reservation")
    public String reservationScreen(Model model){
        model.addAttribute("specialists", specialistRepository.findAll());
        return "reservationScreen";
    }

    @PostMapping("/reservation")
    public String reservationPostCustomer(@Param("specialistId") String specialistId, RedirectAttributes redirectAttributes) {

        Customer newCustomer = customerService.addCustomer(specialistRepository.findById(Integer.parseInt(specialistId)));

        redirectAttributes.addFlashAttribute("message", "Visit limit has been reached");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if(newCustomer == null)
            return "redirect:/reservation";

        redirectAttributes.addFlashAttribute("message", "");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("id", newCustomer.getId());
        redirectAttributes.addFlashAttribute("time", newCustomer.getVisitTime());
        redirectAttributes.addFlashAttribute("code", newCustomer.getPersonalCode());
        return "redirect:/reservation";
    }

    @GetMapping("/reservation/search")
    public String searchCustomer(@Param("personalCode") String personalCode, RedirectAttributes redirectAttributes) {

        if(!customerService.existsByPersonalCode(personalCode)){
            redirectAttributes.addFlashAttribute("message", "Could not found");
            redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/reservation";
        }

        Customer foundCustomer = customerService.findByPersonalCode(personalCode);
        redirectAttributes.addFlashAttribute("message", "Reservation found");
        redirectAttributes.addFlashAttribute("alertClass", "alert-info");
        redirectAttributes.addFlashAttribute("id", foundCustomer.getId());
        redirectAttributes.addFlashAttribute("time", foundCustomer.getVisitTime());
        redirectAttributes.addFlashAttribute("code", foundCustomer.getPersonalCode());
        redirectAttributes.addFlashAttribute("delete", true);
        return "redirect:/reservation";
    }

    @RequestMapping(value = "/reservation/delete", method = RequestMethod.GET)
    public String cancelCustomer(@ModelAttribute("id") String id) {

        customerService.setAsCanceled(Integer.parseInt(id));

        return "redirect:/reservation";
    }
}
