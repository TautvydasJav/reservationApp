package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springframework.reservationApp.services.CustomerService;
import springframework.reservationApp.services.SpecialistService;

@Controller
@AllArgsConstructor
public class DepartmentScreenController {

    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final int departmentScreenCustomerCount = 5;

    @RequestMapping("/department")
    public String departmentScreen(Model model){
        model.addAttribute("specialists", specialistService.findAll());
        model.addAttribute("customers", customerService.getFirstCustomersByTime(departmentScreenCustomerCount));
        return "departmentScreen";
    }
}
