package unifal.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.entity.Room;
import unifal.hotel.services.RoomService;
import unifal.hotel.services.dto.RoomDTO;

import java.util.Objects;

@RestController
public class BookingRestController
{

    private final RoomService roomService;

    @Autowired
    public BookingRestController(RoomService roomService)
    {
        this.roomService = roomService;
    }

    @GetMapping("/home/room/{id}")
    public ResponseEntity<RoomDTO> getRoomInfo(@PathVariable Long id)
    {
        Room room = roomService.getRoomById(id);

        if (Objects.isNull(room)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RoomDTO(room.getId(), room.getType(), room.getRent()));

    }

    @GetMapping("/home/room/")
    public ResponseEntity<RoomDTO> getRoomInfo()
    {
        return ResponseEntity.notFound().build();
    }

}
