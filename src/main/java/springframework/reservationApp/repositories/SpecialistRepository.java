package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.reservationApp.domain.Specialist;

public interface SpecialistRepository extends CrudRepository<Specialist, Long> {
}
