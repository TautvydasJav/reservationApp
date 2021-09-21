package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserPersonalCodeService{

    private final ReservationRepository reservationRepository;

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
        return !reservationRepository.existsByPersonalCode(personalCode);
    }
}
