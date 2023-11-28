package unifal.hotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name="receptionist")
@Getter
@Setter
public class Receptionist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    private Integer rating = 0;
}
