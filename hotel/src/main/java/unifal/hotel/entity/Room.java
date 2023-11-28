package unifal.hotel.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private Integer rent;
}
