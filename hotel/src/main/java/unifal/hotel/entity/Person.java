package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @OneToOne(mappedBy = "person", cascade =  CascadeType.ALL, orphanRemoval = true)
    private Account account;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Client client;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Employee employee;

    public Address getMainAddress()
    {
        if (this.address.isEmpty()) return null;

        return this.address.iterator().next();
    }

}
