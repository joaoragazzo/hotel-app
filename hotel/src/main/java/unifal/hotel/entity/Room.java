package unifal.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import unifal.hotel.services.enums.RoomType;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name="room")
public class Room implements Serializable {
    @Id
    private Long id;
    private RoomType room_type;
    private Integer rent;
}
