package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.book.ControllerDefaultMessage;

import java.util.Objects;

@Controller
public class EmployeeController {

    @GetMapping("/home")
    public String home(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.RECEPTIONIST_OR_MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        return "hotel_employee_page";
    }


}
