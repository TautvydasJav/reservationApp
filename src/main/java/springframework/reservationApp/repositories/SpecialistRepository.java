package springframework.reservationApp.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springframework.reservationApp.domain.Specialist;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends CrudRepository<Specialist, Integer> {

    Specialist findById(int id);
    //Specialist findByUsername(String username);
    Optional<Specialist> findByUsername(String username);
    Optional<Specialist>  findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Specialist a " +
            "SET a.enabled = TRUE WHERE a.username = ?1")
    int enableSpecialist(String username);
}
