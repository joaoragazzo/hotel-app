package unifal.hotel.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.thymeleaf.engine.TemplateManager;
import unifal.hotel.entity.*;
import unifal.hotel.exceptions.*;
import unifal.hotel.repository.jparepository.*;
import unifal.hotel.utils.Security;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class PersonService
{
    private final PersonRepository personRepository;
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ReceptionistRepository receptionistRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional
    public void savePersonAddressAccountEmployeeReceptionist(Person person, Account account, Address address, Employee employee, Receptionist receptionist) {

        if(Objects.isNull(person.getId())) {
            throw new InvalidParameter("The ID field cannot be empty.");
        }

        if (Objects.isNull(person.getCellphone())) {
            throw new InvalidParameter("The cellphone field cannot be empty");
        }

        if (Objects.isNull(address.getZipcode())) {
            throw new InvalidParameter("The zipcode field cannot be empty");
        }

        if(personRepository.existsById(person.getId())) {
            throw new PersonIDAlreadyExists("This person ID already is registered into the database.");
        }

        if(personRepository.existsByCellphone(person.getCellphone())) {
            throw new PersonCellphoneAlreadyExists("This person cellphone is registered into the database.");
        }

        if(accountRepository.existsByEmail(account.getEmail())) {
            log.error("Esse email já existe");
            throw new EmailAlreadyExists("This account email is registered into the database.");
        }

        personRepository.save(person);

        account.setPerson(person);
        account.setPassword(Security.encryptPasswordSHA256(account.getPassword()));

        address.setPerson(person);

        accountRepository.save(account);
        addressRepository.save(address);

        employee.setPerson(person);

        employeeRepository.save(employee);

        receptionist.setEmployee(employee);

        receptionistRepository.save(receptionist);

    }

    public void saveEditedPerson(Person person) throws PersonIDDontExists, PersonCellphoneAlreadyExists
    {
        if(!personRepository.existsById(person.getId())) {
            throw new PersonIDDontExists("This person ID is invalid!");
        }

        if(personRepository.existsByCellphone(person.getCellphone()) &&
                !Objects.equals(person.getCellphone(), personRepository.findById(person.getId()).get().getCellphone())) {
            throw new PersonCellphoneAlreadyExists("This cellphone already is registered on the database.");
        }



        Person oldReferences = personRepository.getReferenceById(person.getId());

        person.setClient(oldReferences.getClient());
        person.setEmployee(oldReferences.getEmployee());
        person.setAddress(oldReferences.getAddress());
        person.setAccount(oldReferences.getAccount());

        personRepository.save(person);

    }

    public Person getPersonByID(Long ID)
    {
        Optional<Person> person = personRepository.findById(ID);

        return person.orElse(null);
    }


    public void deletePerson(Long ID)
    {
        personRepository.deleteById(ID);
    }

}
