package springframework.reservationApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Specialist;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {
    Specialist findById(int id);
    Specialist findByUsername(String username);
}
