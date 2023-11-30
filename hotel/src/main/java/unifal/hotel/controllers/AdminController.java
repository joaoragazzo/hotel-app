package unifal.hotel.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class AdminController {

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView mv = new ModelAndView("hotel_manager_page");

        return mv;
    }


}
