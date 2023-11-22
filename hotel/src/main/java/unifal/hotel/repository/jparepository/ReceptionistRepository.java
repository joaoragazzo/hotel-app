package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {
}
