package unifal.hotel.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Booking;
import unifal.hotel.exceptions.BookingIdDontExists;
import unifal.hotel.exceptions.ConflictBookingDates;
import unifal.hotel.repository.jparepository.BookingRepository;
import unifal.hotel.repository.jparepository.RoomRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;


    public void saveBooking(Booking booking) throws ConflictBookingDates {
        boolean exits = bookingRepository.existsConflictingBooking(
                booking.getRoom().getId(),
                booking.getCheckin_date(),
                booking.getCheckout_date()
        );


        if (exits) {
            List<Booking> bookings = bookingRepository.findConflictingBookings(
                    booking.getRoom().getId(),
                    booking.getCheckin_date(),
                    booking.getCheckout_date()
            );

            StringBuilder message = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (Booking booking_ : bookings) {
                String formattedCheckin = dateFormat.format(booking_.getCheckin_date());
                String formattedCheckout = dateFormat.format(booking_.getCheckout_date());

                message.append(" from ").append(formattedCheckin).append(" to ").append(formattedCheckout).append(", ");
            }

            message.deleteCharAt(message.length() - 2);

            throw new ConflictBookingDates("This room already is reserved" + message + ".");
        }


        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();

    }

    public void deleteBookingById(Integer id) {
        bookingRepository.deleteById(id);
    }

    public Booking findBookingById(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        if(booking.isPresent()) {
            return booking.get();
        }

        throw new BookingIdDontExists("This booking ID doesnt not exists.");
    }

}
