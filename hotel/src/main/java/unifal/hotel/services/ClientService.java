package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService
{
    private final ClientService clientService;

    @Autowired
    public ClientService(ClientService clientService)
    {
        this.clientService = clientService;
    }

}
