package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unifal.hotel.book.HotelBook;
import unifal.hotel.entity.Booking;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>
{

    @Query("SELECT COUNT(b.id) > 0 FROM Booking b WHERE b.room.id = :roomId AND b.checkin_date <= :endDate AND b.checkout_date >= :startDate")
    boolean existsConflictingBooking(
            @Param("roomId") Integer roomId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND (b.checkin_date < :newCheckoutDate AND b.checkout_date > :newCheckinDate)")
    List<Booking> findConflictingBookings(
            @Param("roomId") Integer roomId,
            @Param("newCheckinDate") Date newCheckinDate,
            @Param("newCheckoutDate") Date newCheckoutDate
    );

}
