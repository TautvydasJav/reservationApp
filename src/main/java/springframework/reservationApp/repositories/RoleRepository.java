package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}