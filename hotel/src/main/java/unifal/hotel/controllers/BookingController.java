package unifal.hotel.controllers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import unifal.hotel.exceptions.*;
import unifal.hotel.services.BookingService;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.RoomService;
import unifal.hotel.services.dto.BookingDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
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

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
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

    @GetMapping({"/home/booking/delete/{id}", "/admin/booking/delete/{id}"})
    public String deleteBooking(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);

        bookingService.deleteBookingById(id);

        return "redirect:" + root + "/booking/manager";
    }


    @GetMapping({"/home/booking/edit/{id}", "/admin/booking/edit/{id}"})
    public String editBooking(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";
        model.addAttribute("root", root);
        Booking booking;

        try {
            booking = bookingService.findBookingById(id);
        } catch (BookingIdDontExists e) {
            return "redirect:" + root + "/booking/manager";
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setRoomId(booking.getRoom().getId().longValue());
        bookingDTO.setPersonId(booking.getClient().getPerson().getId());
        bookingDTO.setCheckin(booking.getCheckin_date());
        bookingDTO.setCheckout(booking.getCheckout_date());


        model.addAttribute("bookingObject", bookingDTO);
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("clients", clientService.getAllClientsPersonObject());
        model.addAttribute("bookingIDValue", id);

        return "hotel_booking_edit";

    }


    @PostMapping({"/home/booking/edit/{id}","/admin/booking/edit/{id}"})
    public String editBooking(Model model, HttpSession session, RedirectAttributes redirectAttributes, @ModelAttribute("bookingObject") BookingDTO bookingDTO, @PathVariable Integer id) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        Booking editedBooking = new Booking();
        editedBooking.setId(id);
        editedBooking.setCheckout_date(bookingDTO.getCheckout());

        editedBooking.setCheckin_date(bookingDTO.getCheckin());



        Client client;

        try {
            client = clientService.getClientByPersonId(bookingDTO.getPersonId());
        } catch (PersonIDDontExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to edit a booking: " + e.getMessage());
            return "redirect:" + root + "/booking/edit/" + id;
        }

        Room room;

        try {
            room = roomService.getRoomById(bookingDTO.getRoomId());
        } catch (RoomIDDontExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to edit a booking: " + e.getMessage());
            return "redirect:" + root + "/booking/edit/" + id;
        }

        editedBooking.setClient(client);
        editedBooking.setRoom(room);

        try {
            bookingService.saveBooking(editedBooking);
        } catch (ConflictBookingDates e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to edit a new booking: " + e.getMessage());
            return "redirect:" + root + "/booking/edit/" + id;
        }

        redirectAttributes.addFlashAttribute("successMessage", "The booking was successful edited!");
        return "redirect:" + root + "/booking/edit/" + id;

    }

}
