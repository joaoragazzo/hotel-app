package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
