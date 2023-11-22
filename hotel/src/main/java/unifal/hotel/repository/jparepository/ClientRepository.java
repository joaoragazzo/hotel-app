package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
