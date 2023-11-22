package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.repository.jparepository.ReceptionistRepository;

@Service
public class ReceptionistService
{
    public final ReceptionistRepository receptionistRepository;

    @Autowired
    public ReceptionistService(ReceptionistRepository receptionistRepository)
    {
        this.receptionistRepository = receptionistRepository;
    }

}
