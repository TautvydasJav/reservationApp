package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springframework.reservationApp.domain.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    boolean existsByPersonalCode(String personalCode);
    Customer findByPersonalCode(String var1);
    List<Customer> findAll();
}
