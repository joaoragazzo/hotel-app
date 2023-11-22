package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.repository.jparepository.AddressRepository;

@Service
public class AddressService
{
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

}
