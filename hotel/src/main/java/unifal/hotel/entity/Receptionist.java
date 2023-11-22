package unifal.hotel.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="receptionist")
@Getter
@Setter
public class Receptionist extends Employee {

    @OneToOne
    @JoinColumn(name="employee_id")
    Employee employee;
    Integer rating = 0;
}
