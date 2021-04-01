package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.utils.PersonalCodeUtil;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.utils.TimeUtils;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TimeUtils timeUtils;
    private final PersonalCodeUtil codeUtil;

    public Customer postCustomer(Specialist specialist){
        Customer newCustomer = new Customer();
        newCustomer.setPersonalCode(codeUtil.generateCode());
        codeUtil.checkIfCodeIsUnique(newCustomer);
        newCustomer.setSpecialist(specialist);
        newCustomer.setLocalTime(timeUtils.getNextCustomersTime(specialist));
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public Iterable<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findByPersonalCode(String code){
        return customerRepository.findByPersonalCode(code);
    }

    public boolean existsByPersonalCode (String code){
        return customerRepository.existsByPersonalCode(code);
    }

    public void deleteCustomer (int id){
        customerRepository.delete(customerRepository.findById(id));
    }

}
