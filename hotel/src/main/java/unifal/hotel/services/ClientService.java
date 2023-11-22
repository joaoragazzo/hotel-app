package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.repository.jparepository.ClientRepository;

@Service
public class ClientService
{
    private final ClientRepository clientService;

    @Autowired
    public ClientService(ClientRepository clientRepository)
    {
        this.clientService = clientRepository;
    }

}
