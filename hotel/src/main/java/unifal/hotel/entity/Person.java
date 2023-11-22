package unifal.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name="person")
public class Person implements Serializable {

    @Id
    private Long id;
    private String name;
    private String surname;
    private Long cellphone;
    private String gender;
    private Date birthdate;

    @OneToOne(mappedBy = "person")
    private Account account;

    @OneToOne(mappedBy = "person")
    private Client client;

    @OneToOne(mappedBy = "person")
    private Employee employee;

}
