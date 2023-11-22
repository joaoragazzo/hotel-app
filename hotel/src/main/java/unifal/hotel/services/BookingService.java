package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Booking;
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

    public Boolean saveBooking(Booking booking)
    {
        boolean exits = bookingRepository.existsConflictingBooking(
                booking.getRoom().getId(),
                booking.getCheckin(),
                booking.getCheckout()
        );

        if(!exits) {
            bookingRepository.save(booking);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}
