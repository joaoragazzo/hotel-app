package unifal.hotel.api.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import unifal.hotel.entity.Address;
import unifal.hotel.entity.Client;
import unifal.hotel.entity.Person;
import unifal.hotel.repository.jparepository.PersonRepository;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.PersonService;
import unifal.hotel.services.dto.PersonAddressRegisterDTO;

import java.util.HashSet;
import java.util.Set;

@Controller
public class JPATesting
{
    private final PersonRepository personRepository;
    @Autowired
    public JPATesting(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Autowired
    private PersonService personService;



    @DeleteMapping ("/debug/delete-client/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id)
    {
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/debug/register-person")
    public ModelAndView register_person()
    {
        ModelAndView mv = new ModelAndView("register_client");
        mv.addObject("register", new PersonAddressRegisterDTO());
        return mv;
    }


}
