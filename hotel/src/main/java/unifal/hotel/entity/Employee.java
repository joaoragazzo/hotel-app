package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Setter
@Getter
@Entity
@Table(name="employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Person person;

    @OneToOne(mappedBy = "employee", optional = true)
    private Manager manager;

    @OneToOne(mappedBy = "employee", optional = true)
    private Receptionist receptionist;

    private Date hire_date;
    private Integer salary;

    public Boolean isManager() {
        return !Objects.isNull(manager);
    }

    public Boolean isReceptionist() {
        return !Objects.isNull(receptionist);
    }

}
