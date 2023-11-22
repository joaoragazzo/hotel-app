package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.repository.jparepository.BookingRepository;

@Service
public class BookingService
{
    public final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository)
    {
        this.bookingRepository = bookingRepository;
    }

}
