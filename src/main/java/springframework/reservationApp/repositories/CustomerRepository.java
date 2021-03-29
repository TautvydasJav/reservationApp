package springframework.reservationApp.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.reservationApp.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
