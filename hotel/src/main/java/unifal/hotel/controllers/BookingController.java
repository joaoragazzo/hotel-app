package unifal.hotel.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.RoomService;

@Controller
public class BookingController
{

    private final ClientService clientService;
    private final RoomService roomService;

    public BookingController(ClientService clientService, RoomService roomService) {
        this.clientService = clientService;
        this.roomService = roomService;
    }

    @GetMapping({"/home/booking/register", "/admin/booking/register"})
    public ModelAndView bookingRegister()
    {
        ModelAndView mv = new ModelAndView("hotel_booking_register");

        mv.addObject("clients", clientService.getAllClientsPersonObject());
        mv.addObject("rooms", roomService.getAllRooms());

        return mv;

    }

    @GetMapping({"/home/booking/manager", "/admin/booking/manager"})
    public ModelAndView bookingManager()
    {
        ModelAndView mv = new ModelAndView("hotel_booking_manager");

        return mv;
    }

    @PostMapping({"/home/booking/save", "/admin/booking/save"})
    public String saveNewBooking(RedirectAttributes redirectAttributes)
    {


        // redirectAttributes.addFlashAttribute()

        return "redirect:/home/booking/save";
    }

    @GetMapping({"/home/booking", "/admin/booking"})
    public ModelAndView booking(HttpSession session)
    {

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        ModelAndView mv = new ModelAndView("hotel_booking_page");

        mv.addObject("root", root);

        return mv;
    }


}
