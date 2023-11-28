package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Booking;
import unifal.hotel.exceptions.ConflictBookingDates;
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

    public void saveBooking(Booking booking) throws ConflictBookingDates
    {
        boolean exits = bookingRepository.existsConflictingBooking(
                booking.getRoom().getId(),
                booking.getCheckin(),
                booking.getCheckout()
        );

        if(exits)
            throw new ConflictBookingDates("This room already is reserved from " + booking.getCheckin().toString() + " to " + booking.getCheckout().toString());


        bookingRepository.save(booking);
    }

}
