package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
