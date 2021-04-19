package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.utils.PersonalCodeUtil;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.CustomerRepository;
import springframework.reservationApp.utils.TimeUtils;

import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TimeUtils timeUtils;
    private final PersonalCodeUtil codeUtil;

    public Customer createCustomer(Specialist specialist){
        if(specialist.getCustomers().size() >= timeUtils.getMaxVisitsCount())
            return null;

        Customer newCustomer = new Customer();
        newCustomer.setPersonalCode(codeUtil.generateCode());
        codeUtil.checkIfCodeIsUnique(newCustomer);
        newCustomer.setLocalTime(timeUtils.getNextCustomersTime(specialist));
        newCustomer.setSpecialist(specialist);
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public List<Customer> getAllFirstCustomers(int count){
        List<Customer> allCustomers = customerRepository.findAll();
        Collections.sort(allCustomers);

        List<Customer> firstCustomers = new ArrayList<>();

        if(allCustomers.size() < 5)
            count = allCustomers.size();

        for(int i = 0; i < count; i++){
            firstCustomers.add(allCustomers.get(i));
        }

        return firstCustomers;
    }

    public List<Customer> getFirstCustomers(Specialist specialist){
        Collections.sort(specialist.getCustomers());

        return specialist.getCustomers();
    }


    public Customer findByPersonalCode(String code){
        return customerRepository.findByPersonalCode(code);
    }

    public boolean existsByPersonalCode (String code){
        return customerRepository.existsByPersonalCode(code);
    }

    public void deleteCustomerById (int id){
        customerRepository.deleteById(id);
    }
}
