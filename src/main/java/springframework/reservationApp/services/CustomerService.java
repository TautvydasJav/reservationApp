package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.utils.PersonalCodeUtil;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.utils.TimeUtils;
import static springframework.reservationApp.enums.CustomerStatus.*;

import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TimeUtils timeUtils;
    private final PersonalCodeUtil codeUtil;

    public Customer addCustomer(Specialist specialist){
        if(specialist.getCustomers().size() >= timeUtils.getMaxVisitsCount())
            return null;

        Customer newCustomer = new Customer(codeUtil.generateCode()
                , timeUtils.getNextCustomersTime(customerRepository.findBySpecialistAndStatus(specialist, WAITING.name()))
                , specialist);

        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public List<Customer> getAvailable(Specialist specialist){
        List<Customer> customers = customerRepository.findBySpecialistAndStatus(specialist, WAITING.name());
        Collections.sort(customers);

        return customers;
    }

    public void setAsCanceled(int id){
        Customer customer = customerRepository.findById(id);
        customer.setAsCanceled();
        customerRepository.save(customer);
    }

    public Customer findByPersonalCode(String code){
        return customerRepository.findByPersonalCode(code);
    }

    public boolean existsByPersonalCode (String code){
        return customerRepository.existsByPersonalCode(code);
    }

}
