package springframework.reservationApp.utils;

import org.springframework.stereotype.Component;
import springframework.reservationApp.domain.Customer;
import springframework.reservationApp.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class PersonalCodeUtil{

    private final CustomerRepository customerRepository;

    public PersonalCodeUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String generateCode(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=1; i<10; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        String personalCode = list.get(0).toString()+list.get(1).toString()+list.get(2).toString()+list.get(3).toString();

        if(checkIfCodeIsUnique(personalCode)){
            return personalCode;
        }
        else personalCode = generateCode();

        return personalCode;
    }

    public boolean checkIfCodeIsUnique(String personalCode) {
        if(customerRepository.existsByPersonalCode(personalCode)) {
            return false;
        }
        else
            return true;
    }
}
