package springframework.reservationApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springframework.reservationApp.repositories.CustomerRepository;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/customersList")
    public String getCustomers(Model model){

        model.addAttribute("customers", customerRepository.findAll());

        return "customer/list";
    }

    @RequestMapping("/customersAdd")
    public String addCustomers(Model model){

        model.addAttribute("customers", customerRepository.findAll());

        return "customer/add";
    }
}
