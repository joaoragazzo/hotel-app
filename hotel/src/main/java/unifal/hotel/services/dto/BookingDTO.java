package unifal.hotel.services.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import unifal.hotel.entity.Booking;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO
{

    private Long personId;
    private Long roomId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkout;



}
