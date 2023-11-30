package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{

    Optional<Account> getAccountByEmailAndPassword(String email, String password_hash);
}
