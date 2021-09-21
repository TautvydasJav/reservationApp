package springframework.reservationApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.reservationApp.domain.Reservation;
import springframework.reservationApp.domain.Specialist;
import springframework.reservationApp.repositories.ReservationRepository;
import springframework.reservationApp.utils.TimeUtils;

import static springframework.reservationApp.enums.CustomerStatus.*;

import java.util.*;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserPersonalCodeService personalCodeService;

    public Reservation addCustomer(Specialist specialist){
        if(specialist.getReservations().size() >= TimeUtils.getMaxVisitsCount())
            return null;

        Reservation newReservation = new Reservation(personalCodeService.generateCode()
                , TimeUtils.getNextCustomersTime(reservationRepository.findBySpecialistAndStatus(specialist, WAITING.name()))
                , specialist);

        reservationRepository.save(newReservation);
        return newReservation;
    }

    public List<Reservation> getAvailable(Specialist specialist){
        List<Reservation> reservations = reservationRepository.findBySpecialistAndStatus(specialist, WAITING.name());
        Collections.sort(reservations);

        return reservations;
    }

    public void setAsCanceled(int id){
        Reservation reservation = reservationRepository.findById(id);
        reservation.setAsCanceled();
        reservationRepository.save(reservation);
    }

    public Reservation findByPersonalCode(String code){
        return reservationRepository.findByPersonalCode(code);
    }

    public boolean existsByPersonalCode (String code){
        return reservationRepository.existsByPersonalCode(code);
    }

}
