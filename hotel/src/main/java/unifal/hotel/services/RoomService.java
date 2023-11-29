package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Room;
import unifal.hotel.repository.jparepository.RoomRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<Room> getAllRooms()
    {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id)
    {
        Optional<Room> room = roomRepository.findById(id);

        return room.orElse(null);

    }

}
