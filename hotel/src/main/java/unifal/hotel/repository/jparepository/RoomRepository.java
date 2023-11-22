package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>
{

}
