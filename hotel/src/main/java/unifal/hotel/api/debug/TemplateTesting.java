package unifal.hotel.api.debug;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import unifal.hotel.api.debug.arbitraryClasses.PersonArbitrary;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TemplateTesting
{


    @GetMapping("/debug/render-template")
    public ModelAndView people(Model model)
    {
        List<PersonArbitrary> people = new ArrayList<>();

        people.add(new PersonArbitrary(1, "João"));
        people.add(new PersonArbitrary(2, "Pedro"));
        people.add(new PersonArbitrary(3, "Matheus"));

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("people", people);

        return mv;
    }

}
