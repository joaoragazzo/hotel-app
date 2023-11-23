package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Client;
import unifal.hotel.repository.jparepository.ClientRepository;

@Service
public class ClientService
{
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository)
    {
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

}
