package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.exceptions.PersonCellphoneAlreadyExists;
import unifal.hotel.exceptions.PersonIDDontExists;
import unifal.hotel.repository.jparepository.PersonRepository;
import unifal.hotel.entity.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonService
{
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public void savePerson(Person person)
    {
        personRepository.save(person);
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
