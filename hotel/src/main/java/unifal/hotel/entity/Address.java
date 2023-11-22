package unifal.hotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="address")
public class Address implements Serializable {

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
