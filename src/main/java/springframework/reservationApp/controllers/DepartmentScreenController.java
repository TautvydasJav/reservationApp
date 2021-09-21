package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springframework.reservationApp.repositories.ReservationRepository;
import springframework.reservationApp.repositories.SpecialistRepository;

import static springframework.reservationApp.enums.CustomerStatus.WAITING;

@Controller
@AllArgsConstructor
public class DepartmentScreenController {

    private final SpecialistRepository specialistRepository;
    private final ReservationRepository reservationRepository;

    @RequestMapping("/department")
    public String departmentScreen(Model model){
        model.addAttribute("specialists", specialistRepository.findAll());
        model.addAttribute("reservations", reservationRepository.findFirst5ByStatusOrderByVisitTimeAsc(WAITING.name()));
        return "departmentScreen";
    }
}
