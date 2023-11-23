package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name="client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "client")
    private Set<Booking> booking;

}
