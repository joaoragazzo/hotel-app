package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.book.ControllerDefaultMessage;
import unifal.hotel.entity.Booking;
import unifal.hotel.entity.Client;
import unifal.hotel.entity.Room;
import unifal.hotel.exceptions.ConflictBookingDates;
import unifal.hotel.exceptions.PersonIDDontExists;
import unifal.hotel.exceptions.RoomIDDontExists;
import unifal.hotel.services.BookingService;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.RoomService;
import unifal.hotel.services.dto.BookingDTO;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class BookingController {

    private final ClientService clientService;
    private final BookingService bookingService;
    private final RoomService roomService;


    @GetMapping({"/home/booking/register", "/admin/booking/register"})
    public String bookingRegister(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        BookingDTO booking = new BookingDTO();
        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        model.addAttribute("clients", clientService.getAllClientsPersonObject());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("root", root);
        model.addAttribute("bookingObject", booking);

        return "hotel_booking_register";

    }

    @GetMapping({"/home/booking/manager", "/admin/booking/manager"})
    public String bookingManager(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }
        List<Booking> bookings = bookingService.getAllBookings();

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        model.addAttribute("bookings", bookings);

        return "hotel_booking_manager";
    }

    @PostMapping({"/home/booking/save", "/admin/booking/save"})
    public String saveNewBooking(RedirectAttributes redirectAttributes, HttpSession session, @ModelAttribute("bookingObject") BookingDTO bookingDTO) {

        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home" ;
        Client client;
        Room room;

        try {
            client = clientService.getClientByPersonId(bookingDTO.getPersonId());
        } catch (PersonIDDontExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to make a new booking: " + e.getMessage());
            return "redirect:" + root + "/booking/register";
        }

        try {
            room = roomService.getRoomById(bookingDTO.getRoomId());
        } catch (RoomIDDontExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to make a new booking: " + e.getMessage());
            return "redirect:" + root + "/booking/register";
        }

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setCheckin_date(bookingDTO.getCheckin());
        booking.setCheckout_date(bookingDTO.getCheckout());
        booking.setRoom(room);

        try {
            bookingService.saveBooking(booking);
        } catch (ConflictBookingDates e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to maake a new booking: " + e.getMessage());
            return "redirect:" + root + "/booking/register";
        }


        redirectAttributes.addFlashAttribute("successMessage", "A new booking was successful saved!");

        return "redirect:" + root + "/booking/register";
    }

    @GetMapping({"/home/booking", "/admin/booking"})
    public String booking(Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        return "hotel_booking_page";
    }

    @GetMapping({"/home/booking/delete/{id}","/admin/booking/delete/{id}"})
    public String deleteBooking(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Integer id)
    {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        bookingService.deleteBooking(id);

        return "redirect:" + root + "/booking/manager";
    }




}
