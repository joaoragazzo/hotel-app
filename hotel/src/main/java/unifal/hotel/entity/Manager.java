package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="manager")
@Getter
@Setter
public class Manager extends Employee {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
}
