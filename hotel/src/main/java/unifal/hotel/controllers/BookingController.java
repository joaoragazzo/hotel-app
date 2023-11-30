package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.book.ControllerDefaultMessage;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.RoomService;

import java.util.Objects;

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
    public String bookingRegister(Model model, RedirectAttributes redirectAttributes, HttpSession session)
    {
        if(Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        model.addAttribute("clients", clientService.getAllClientsPersonObject());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("root", root);

        return "hotel_booking_register";

    }

    @GetMapping({"/home/booking/manager", "/admin/booking/manager"})
    public String bookingManager(Model model, RedirectAttributes redirectAttributes, HttpSession session)
    {
        if(Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        return "hotel_booking_manager";
    }

    @PostMapping({"/home/booking/save", "/admin/booking/save"})
    public String saveNewBooking(RedirectAttributes redirectAttributes, HttpSession session)
    {

        if(Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }


        // redirectAttributes.addFlashAttribute()

        return "redirect:/home/booking/save";
    }

    @GetMapping({"/home/booking", "/admin/booking"})
    public String booking(Model model, RedirectAttributes redirectAttributes, HttpSession session)
    {

        if(Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        return "hotel_booking_page";
    }


}
