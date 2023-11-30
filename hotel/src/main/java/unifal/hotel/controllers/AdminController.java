package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class AdminController {
    @GetMapping("/admin")
    public ModelAndView admin(HttpSession session) {

        ModelAndView mv = new ModelAndView("hotel_manager_page");

        mv.addObject("root", "admin");

        return mv;
    }


}
