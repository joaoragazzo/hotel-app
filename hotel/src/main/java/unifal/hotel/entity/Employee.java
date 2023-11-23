package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;

    @OneToOne(mappedBy = "employee")
    private Manager manager;

    @OneToOne(mappedBy = "employee")
    private Receptionist receptionist;

    private Date hire_date;
    private Integer salary;

}
