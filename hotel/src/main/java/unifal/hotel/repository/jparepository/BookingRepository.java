package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unifal.hotel.book.HotelBook;
import unifal.hotel.entity.Booking;

import java.util.Date;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>
{

    @Query(HotelBook.CHECK_IF_EXIST_BOOKING_CONFLICTS)
    boolean existsConflictingBooking(
            @Param("roomId") Long roomId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
