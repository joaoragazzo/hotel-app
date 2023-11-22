package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
