package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}