package unifal.hotel.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RoomDTO
{
    private Integer id;
    private String room_type;
    private Integer rent;

}
