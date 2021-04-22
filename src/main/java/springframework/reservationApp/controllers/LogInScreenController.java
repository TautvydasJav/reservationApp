package springframework.reservationApp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class LogInScreenController {

    @GetMapping("/login")
    public String login() {
        return "login";
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
