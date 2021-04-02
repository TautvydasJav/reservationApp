package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Specialist;

@Repository
public interface SpecialistRepository extends CrudRepository<Specialist, Integer> {
    Specialist findById(int id);
    Specialist findByUsername(String username);
}
