package unifal.hotel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Address;
import unifal.hotel.repository.jparepository.AddressRepository;

@AllArgsConstructor
@Service
public class AddressService
{
    private final AddressRepository addressRepository;

    public void saveEditedAddress(Address address) {
        addressRepository.save(address);
    }

}
