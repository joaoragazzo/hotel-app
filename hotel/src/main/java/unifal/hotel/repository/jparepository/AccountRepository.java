package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{

}
