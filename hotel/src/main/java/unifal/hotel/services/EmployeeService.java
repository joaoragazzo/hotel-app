package unifal.hotel.services;

import org.springframework.stereotype.Service;
import unifal.hotel.repository.jparepository.EmployeeRepository;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

}
