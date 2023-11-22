package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.services.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
