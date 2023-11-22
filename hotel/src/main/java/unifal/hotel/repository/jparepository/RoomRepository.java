package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
