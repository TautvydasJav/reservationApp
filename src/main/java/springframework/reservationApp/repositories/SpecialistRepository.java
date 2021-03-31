package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.reservationApp.domain.Specialist;

import java.util.Optional;

public interface SpecialistRepository extends CrudRepository<Specialist, Integer> {

    public Specialist findById(int id);
}
