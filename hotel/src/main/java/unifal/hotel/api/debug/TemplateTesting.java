package unifal.hotel.api.debug;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import unifal.hotel.api.debug.arbitraryClasses.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class TemplateTesting
{

    List<Person> people = new ArrayList<>();

    @GetMapping("/debug/render-template")
    public ModelAndView people(Model model)
    {
        people.add(new Person(1, "João"));
        people.add(new Person(2, "Pedro"));
        people.add(new Person(3, "Matheus"));

        ModelAndView mv = new ModelAndView("index");
        mv.addObject("people", people);

        return mv;
    }

}
