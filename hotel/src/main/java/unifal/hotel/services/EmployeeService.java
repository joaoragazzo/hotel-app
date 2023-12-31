package unifal.hotel.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Employee;
import unifal.hotel.entity.Person;
import unifal.hotel.exceptions.EmailAlreadyExists;
import unifal.hotel.exceptions.PersonCellphoneAlreadyExists;
import unifal.hotel.exceptions.PersonIDAlreadyExists;
import unifal.hotel.repository.jparepository.AccountRepository;
import unifal.hotel.repository.jparepository.EmployeeRepository;
import unifal.hotel.repository.jparepository.PersonRepository;
import unifal.hotel.repository.jparepository.ReceptionistRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public void deleteEmployeeByReceptionistId(Long id) {
        employeeRepository.deleteByReceptionistId(id);
    }

    public void saveEditedEmployee (Employee employee) {
        employeeRepository.save(employee);
    }
}
