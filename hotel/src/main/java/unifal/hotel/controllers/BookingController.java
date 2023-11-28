package unifal.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookingController
{

    @GetMapping
    public ModelAndView bookingManager()
    {
        ModelAndView mv = new ModelAndView("hotel_booking");

        return mv;

    }


}
