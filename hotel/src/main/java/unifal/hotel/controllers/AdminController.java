package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.book.ControllerDefaultMessage;

import java.util.Objects;

@Log4j2
@Controller
public class AdminController {
    @GetMapping("/admin")
    public String admin(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {

            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "admin");

        return "hotel_manager_page";
    }


}
