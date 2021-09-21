package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springframework.reservationApp.domain.Reservation;
import springframework.reservationApp.repositories.SpecialistRepository;
import springframework.reservationApp.services.ReservationService;

@Controller
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationScreenController {

    private final ReservationService reservationService;
    private final SpecialistRepository specialistRepository;

    @GetMapping
    public String reservationScreen(Model model){
        model.addAttribute("specialists", specialistRepository.findAll());
        return "reservationScreen";
    }

    @GetMapping(path = "/search")
    public String findCustomer(@Param("personalCode") String personalCode, RedirectAttributes redirectAttributes) {
        if(!reservationService.existsByPersonalCode(personalCode)){
            redirectAttributes.addFlashAttribute("message", "Could not found");
            redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/reservation";
        }

        Reservation foundReservation = reservationService.findByPersonalCode(personalCode);
        redirectAttributes.addFlashAttribute("message", "Reservation found");
        redirectAttributes.addFlashAttribute("alertClass", "alert-info");
        redirectAttributes.addFlashAttribute("id", foundReservation.getId());
        redirectAttributes.addFlashAttribute("time", foundReservation.getVisitTime());
        redirectAttributes.addFlashAttribute("code", foundReservation.getPersonalCode());
        redirectAttributes.addFlashAttribute("delete", foundReservation.isActive());
        return "redirect:/reservation";
    }
    @PostMapping
    public String postCustomer(@Param("specialistId") String specialistId, RedirectAttributes redirectAttributes) {
        Reservation newReservation = reservationService.createReservation(specialistRepository.findById(Integer.parseInt(specialistId)));

        redirectAttributes.addFlashAttribute("message", "Visit limit has been reached");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if(newReservation == null)
            return "redirect:/reservation";

        redirectAttributes.addFlashAttribute("message", "");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("id", newReservation.getId());
        redirectAttributes.addFlashAttribute("time", newReservation.getVisitTime());
        redirectAttributes.addFlashAttribute("code", newReservation.getPersonalCode());
        return "redirect:/reservation";
    }

    @RequestMapping("/delete")
    public String cancelCustomer(@Param("id") String id) {
        System.out.println(id);
        reservationService.setAsCanceled(Integer.parseInt(id));

        return "redirect:/reservation";
    }
}
