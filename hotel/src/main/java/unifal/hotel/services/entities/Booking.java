package unifal.hotel.services.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
@Entity
@Table(name="booking")
public class Booking {

    @Id
    private Long id;
    private Date checkin;
    private Date checkout;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
}
