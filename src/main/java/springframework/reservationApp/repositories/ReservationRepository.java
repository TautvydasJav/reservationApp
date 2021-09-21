package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Reservation;
import springframework.reservationApp.domain.Specialist;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    boolean existsByPersonalCode(String personalCode);
    Reservation findByPersonalCode(String var1);
    List<Reservation> findAll();
    List<Reservation> findFirst5ByStatusOrderByVisitTimeAsc(String status);
    List<Reservation> findBySpecialistAndStatus(Specialist specialist, String status);
    Reservation findById(int id);

}
