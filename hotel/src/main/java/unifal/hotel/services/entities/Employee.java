package unifal.hotel.services.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="employee")
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;
    private Date hire_date;
    private Integer salary;

}
