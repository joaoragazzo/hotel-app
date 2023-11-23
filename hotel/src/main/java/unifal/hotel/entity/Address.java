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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;
    private String street;
    private String neighborhood;
    private Long zipcode;
    private String country;
    private String city;
    private String state;
}
