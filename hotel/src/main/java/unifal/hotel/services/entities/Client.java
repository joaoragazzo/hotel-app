package unifal.hotel.services.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name="client")
public class Client {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person person;

}
