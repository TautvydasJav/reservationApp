package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class LogInScreenController {

    @GetMapping("/login")
    public ModelAndView loginSpecialist() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/login");
        return modelAndView;
    }

    @RequestMapping("/default")
    public String defaultAfterLogin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasSpecialistRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("SPECIALIST"));

        if (hasSpecialistRole) {
            return "redirect:/specialist";
        }
        return "redirect:/department";
    }

}
