package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Room;
import unifal.hotel.repository.jparepository.RoomRepository;

@Service
public class RoomService
{
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(Room room)
    {
        return roomRepository.save(room);
    }

}
