package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
