package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.entity.Address;
import unifal.hotel.entity.Person;
import unifal.hotel.exceptions.PersonCellphoneAlreadyExists;
import unifal.hotel.exceptions.PersonIDAlreadyExists;
import unifal.hotel.exceptions.PersonIDDontExists;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.PersonService;
import unifal.hotel.services.dto.PersonAddressRegisterDTO;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ClientController
{

    private final ClientService clientService;
    private final PersonService personService;

    @Autowired
    public ClientController(ClientService clientService, PersonService personService) {
        this.clientService = clientService;
        this.personService = personService;
    }


    @GetMapping({"/home/client/register", "/admin/client/register"})
    public ModelAndView registerNewClientForm()
    {
        ModelAndView mv = new ModelAndView("hotel_client_register");
        mv.addObject("RegisterDTO", new PersonAddressRegisterDTO());
        return mv;
    }

    @PostMapping({"/home/client/register", "/admin/client/register"})
    public String registerNewClientRegister(@ModelAttribute("RegisterDTO") PersonAddressRegisterDTO data,
                                            RedirectAttributes redirectAttributes, HttpSession session)
    {

        String redirectUrl = session.getAttribute("role").equals("admin") ? "/admin/client/register" :
                "/home/client/register";

        try {
            Person newPerson = data.getPerson();
            Address newAddress = data.getAddress();

            Set<Address> addresses = new HashSet<>();


            newAddress.setPerson(newPerson);
            addresses.add(newAddress);

            newPerson.setAddress(addresses);

            clientService.saveNewClientByPersonObject(newPerson);


            redirectAttributes.addFlashAttribute("successMessage", "The client was successful added to the database.");
        } catch (PersonCellphoneAlreadyExists | PersonIDAlreadyExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to register a new client: " + e.getMessage());
        }

        return "redirect:" + redirectUrl;
    }

    @GetMapping({"/home/client/manager", "/admin/client/manager"})
    public ModelAndView viewAllClients(HttpSession session)
    {
        ModelAndView mv = new ModelAndView("hotel_client_manager");

        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        mv.addObject("clientPersonList", clientService.getAllClientsPersonObject());
        mv.addObject("root", root);


        return mv;
    }

    @GetMapping({"/home/client/edit/{id}", "/admin/client/edit/{id}"})
    public ModelAndView editClient(@PathVariable String id)
    {
        ModelAndView mv = new ModelAndView("hotel_client_edit");

        Person person = personService.getPersonByID(Long.parseLong(id));

        PersonAddressRegisterDTO personAddressRegisterDTO = new PersonAddressRegisterDTO();

        personAddressRegisterDTO.setAddress(person.getAddress().iterator().next());
        personAddressRegisterDTO.setPerson(person);

        mv.addObject("RegisterDTO", personAddressRegisterDTO);

        return mv;
    }

    @PostMapping({"/home/client/edit", "/admin/client/edit"})
    public String editClientSave(@ModelAttribute("RegisterDTO") PersonAddressRegisterDTO data,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session)
    {
        String redirectURL = session.getAttribute("role").equals("admin") ? "/admin/client/edit/" :
                "/home/client/edit/";

        try {
            Person newPerson = data.getPerson();
            Address newAddress = data.getAddress();

            Set<Address> addresses = new HashSet<>();

            newAddress.setPerson(newPerson);
            addresses.add(newAddress);

            newPerson.setAddress(addresses);

            personService.saveEditedPerson(newPerson);

            redirectAttributes.addFlashAttribute("successMessage", "The client was successful edited to the database.");
        } catch (PersonCellphoneAlreadyExists | PersonIDDontExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to register a new client: " + e.getMessage());
        }

        return "redirect:" + redirectURL + data.getPerson().getId().toString();
    }

    @GetMapping({"/home/client/delete/{id}", "/admin/client/delete/{id}"})
    public String deleteClient(@PathVariable String id, HttpSession session)
    {
        String redirectURL = session.getAttribute("role").equals("admin") ? "/admin/client/manager" : "/home/client/manager";

        personService.deletePerson(Long.parseLong(id));

        return "redirect:" + redirectURL;
    }

    @GetMapping({"/home/client", "/admin/client"})
    public ModelAndView client(HttpSession session)
    {
        String root = session.getAttribute("role").equals("admin") ? "/admin" : "/home";

        ModelAndView mv = new ModelAndView("hotel_client_page");

        mv.addObject("root", root);

        return mv;
    }
}
