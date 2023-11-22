package unifal.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="manager")
@Getter
@Setter
public class Manager extends Employee implements Serializable {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
}
