package unifal.hotel.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Receptionist;
import unifal.hotel.repository.jparepository.ReceptionistRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ReceptionistService
{
    public final ReceptionistRepository receptionistRepository;


    public List<Receptionist> findAllReceptionist() {
        return receptionistRepository.findAll();
    }


}
