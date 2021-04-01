package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.services.CustomerService;
import springframework.reservationApp.services.SpecialistService;
import springframework.reservationApp.domain.Customer;

@Controller
@AllArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;
    private final CustomerService customerService;

    @RequestMapping("/reservation")
    public String getSpecialists(Model model){
        model.addAttribute("specialists", specialistService.findAll());
        return "customer/reservationScreen";
    }

    @PostMapping("/reservation")
    public String postCustomer(Model model, @Param("specialistId") String specialistId, RedirectAttributes redirectAttributes) {

        System.out.println("specialist id" +specialistId);
        model.addAttribute("specialists", specialistService.findAll());

        redirectAttributes.addFlashAttribute("message", "Failed");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if (!specialistService.existsById(Integer.parseInt(specialistId))) {
            return "redirect:/reservation";
        }

        Customer newCustomer = customerService.postCustomer(specialistService.findById(Integer.parseInt(specialistId)));

        redirectAttributes.addFlashAttribute("message", "Booked");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("id", newCustomer.getId());
        redirectAttributes.addFlashAttribute("time", newCustomer.getLocalTime());
        redirectAttributes.addFlashAttribute("code", newCustomer.getPersonalCode());
        return "redirect:/reservation";
    }

    @GetMapping("/search")
    public String search(Model model, @Param("personalCode") String personalCode, RedirectAttributes redirectAttributes) {

        model.addAttribute("specialists", specialistService.findAll());

        if(!customerService.existsByPersonalCode(personalCode)){
            redirectAttributes.addFlashAttribute("message", "Could not found");
            redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/reservation";
        }

        Customer foundCustomer = customerService.findByPersonalCode(personalCode);
        redirectAttributes.addFlashAttribute("message", "Reservation found");
        redirectAttributes.addFlashAttribute("alertClass", "alert-info");
        redirectAttributes.addFlashAttribute("id", foundCustomer.getId());
        redirectAttributes.addFlashAttribute("time", foundCustomer.getLocalTime());
        redirectAttributes.addFlashAttribute("code", foundCustomer.getPersonalCode());
        redirectAttributes.addFlashAttribute("delete", true);
        return "redirect:/reservation";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteCustomer(Model model, @ModelAttribute("id") String id) {

        customerService.deleteCustomer(Integer.parseInt(id));
        model.addAttribute("specialists", specialistService.findAll());
        return "redirect:/reservation";
    }

    @GetMapping("/login")
    public String login() {
       System.out.println("veikia");
       return "customer/login";
    }
    @PostMapping("/login/submit")
    public String submitLogin(@Param("username") String username, @Param("password") String password, RedirectAttributes redirectAttributes) {
        System.out.println(username);
        System.out.println(password);

        return "redirect:/login";
    }

    /*
    @PostMapping("/login")
    public String login(@RequestBody RegistrationRequest request){


        return "customer/login";
    }

     */
}