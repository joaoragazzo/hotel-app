package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{

}
