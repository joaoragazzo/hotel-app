package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Client;
import unifal.hotel.entity.Person;
import unifal.hotel.exceptions.PersonCellphoneAlreadyExists;
import unifal.hotel.exceptions.PersonIDAlreadyExists;
import unifal.hotel.exceptions.PersonIDDontExists;
import unifal.hotel.repository.jparepository.ClientRepository;
import unifal.hotel.repository.jparepository.PersonRepository;

import java.util.*;

@Service
public class ClientService
{
    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, PersonRepository personRepository)
    {
        this.personRepository = personRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean deleteClient(Client client)
    {
        try {
            clientRepository.delete(client);
            return Boolean.TRUE;
        } catch (RuntimeException e) {
            return Boolean.FALSE;
        }
    }

    public Boolean deleteClientByPersonID(Long ID)
    {
        Integer n = clientRepository.deleteByPersonId(ID);

        if (n == 0) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public void saveNewClientByPersonObject(Person person) throws PersonIDAlreadyExists, PersonCellphoneAlreadyExists
    {



        if (personRepository.existsById(person.getId())) {
            throw new PersonIDAlreadyExists("This ID is already been used by another person.");
        }

        if (personRepository.existsByCellphone(person.getCellphone())) {

            throw new PersonCellphoneAlreadyExists("This cellphone is already been used by another person.");
        }


        person = personRepository.save(person);

        Client newClient = new Client();
        newClient.setPerson(person);
        person.setClient(newClient);

        clientRepository.save(newClient);

    }

    public ArrayList<Person> getAllClientsPersonObject()
    {
        Set<Client> clients = new HashSet<Client>(clientRepository.findAll());
        ArrayList<Person> persons = new ArrayList<>();


        for(Client client:clients) {
            persons.add(client.getPerson());
        }

        persons.sort(Comparator.comparing(Person::getId));

        return persons;
    }

    public Client getClientByPersonId(Long id) throws PersonIDDontExists {

        Client client = clientRepository.getClientByPersonId(id);

        if (!Objects.isNull(client)) {
            return client;
        }

        throw new PersonIDDontExists("This person ID dont exists.");

    }


}
