package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.domain.Reservation;
import springframework.reservationApp.services.ReservationService;
import springframework.reservationApp.services.SpecialistService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/specialist")
public class SpecialistScreenController {

    private final SpecialistService specialistService;
    private final ReservationService reservationService;

    @RequestMapping
    public String getSpecialistScreen(Model model){
        model.addAttribute("reservations",  reservationService.getAvailableReservations());
        model.addAttribute("status", specialistService.isInVisit());
        return "specialistScreen";
    }

    @RequestMapping(path = "/start")
    public String startVisit(@ModelAttribute("type") String type, RedirectAttributes redirectAttributes){
        if(specialistService.checkForAvailableVisit(reservationService.getAvailableReservations())) {
            specialistService.setStartOfVisit();
        }
        else{
            redirectAttributes.addFlashAttribute("error", "No visits for now");
        }

        return "redirect:/specialist";
    }

    @RequestMapping(path = "/end")
    public String endVisit(@ModelAttribute("type") String type){
        if(type.equals("END")){
            specialistService.setEndOfVisit();
        }
        return "redirect:/specialist";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteCustomerFromSpecialistScreen(@ModelAttribute("id") String id){
        reservationService.setAsCanceled(Integer.parseInt(id));
        return "redirect:/specialist";
    }
}
