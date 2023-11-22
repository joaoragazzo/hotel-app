package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{

}
