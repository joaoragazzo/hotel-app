package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>
{

}
