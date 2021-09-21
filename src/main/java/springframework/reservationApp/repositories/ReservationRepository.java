package springframework.reservationApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Reservation;
import springframework.reservationApp.domain.Specialist;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByPersonalCode(String personalCode);
    Reservation findByPersonalCode(String var1);
    List<Reservation> findFirst5ByStatusOrderByVisitTimeAsc(String status);
    List<Reservation> getAllBySpecialistAndStatus(Specialist specialist, String status);
    Reservation findById(int id);

}
