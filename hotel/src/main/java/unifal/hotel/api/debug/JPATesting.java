package unifal.hotel.api.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import unifal.hotel.repository.jparepository.PersonRepository;

@Controller
public class JPATesting
{
    private final PersonRepository personRepository;
    @Autowired
    public JPATesting(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/debug/test-jpa")
    public ModelAndView jpa_test()
    {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("people", personRepository.findAll());
        return mv;
    }
}
