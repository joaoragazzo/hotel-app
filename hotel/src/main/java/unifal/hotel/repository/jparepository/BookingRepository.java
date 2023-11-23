package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unifal.hotel.book.HotelBook;
import unifal.hotel.entity.Booking;

import java.util.Date;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>
{

    @Query("SELECT COUNT(b.id) > 0 FROM Booking b WHERE b.id = :roomId AND NOT (b.checkout <= :startDate OR b.checkin >= :endDate)")
    boolean existsConflictingBooking(
            @Param("roomId") Integer roomId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
