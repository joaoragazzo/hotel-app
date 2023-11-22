package unifal.hotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="address")
public class Address {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person person;
    private String street;
    private String neighborhood;
    private Long zip;
    private String country;
    private String city;
}
