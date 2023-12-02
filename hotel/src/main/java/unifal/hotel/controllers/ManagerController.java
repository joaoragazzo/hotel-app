package unifal.hotel.controllers;

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
import unifal.hotel.entity.*;
import unifal.hotel.exceptions.EmailAlreadyExists;
import unifal.hotel.exceptions.InvalidParameter;
import unifal.hotel.exceptions.PersonCellphoneAlreadyExists;
import unifal.hotel.exceptions.PersonIDAlreadyExists;
import unifal.hotel.services.*;
import unifal.hotel.services.dto.EmployeeRegisterDTO;
import unifal.hotel.services.formatters.PhoneFormatter;

import java.util.*;

@Log4j2
@Controller
@AllArgsConstructor
public class ManagerController {

    private final EmployeeService employeeService;
    private final PersonService personService;
    private final ReceptionistService receptionistService;
    private final AccountService accountService;
    private final AddressService addressService;

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {

            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "admin");

        return "hotel_manager_page";
    }

    @GetMapping("/admin/employee")
    public String employee(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");

        return "hotel_admin_employee_page";

    }

    @GetMapping("/admin/employee/register")
    public String employeeRegister(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        EmployeeRegisterDTO employeeRegister = new EmployeeRegisterDTO();

        employeeRegister.setPerson(new Person());
        employeeRegister.getPerson().setEmployee(new Employee());
        employeeRegister.getPerson().setAccount(new Account());

        model.addAttribute("person", employeeRegister);
        model.addAttribute("root", "/admin");

        return "hotel_admin_employee_register";
    }

    @PostMapping("/admin/employee/register")
    public String employeeRegister(Model model, HttpSession session, RedirectAttributes redirectAttributes, @ModelAttribute("employeeRegister") EmployeeRegisterDTO employeeRegisterDTO) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");

        if (!Objects.equals(employeeRegisterDTO.getAccount().getPassword(), employeeRegisterDTO.getConfirm_password())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You need to confirm the password correctly.");
            return "redirect:/admin/employee/register";
        }

        Person person = employeeRegisterDTO.getPerson();
        Address address = employeeRegisterDTO.getAddress();
        Account account = employeeRegisterDTO.getAccount();
        Employee employee = employeeRegisterDTO.getEmployee();


        try {
            personService.savePersonAddressAccountEmployeeReceptionist(person, account, address, employee, new Receptionist());
        } catch (PersonIDAlreadyExists | PersonCellphoneAlreadyExists | EmailAlreadyExists | InvalidParameter e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error occurs when trying to create a new employee: " + e.getMessage());
            return "redirect:/admin/employee/register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "A new employee was successful registered.");
        return "redirect:/admin/employee/register";
    }

    @GetMapping("/admin/employee/manager")
    public String employeeManager(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");
        model.addAttribute("receptionists", receptionistService.findAllReceptionist());

        return "hotel_admin_employee_manager";
    }


    @GetMapping("/admin/employee/delete/{id}")
    public String employeeDelete(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");

        personService.deletePerson(id);

        return "redirect:/admin/employee/manager";
    }

    @GetMapping("/admin/employee/edit/{id}")
    public String employeeEdit(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id)
    {
        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");

        EmployeeRegisterDTO employeeRegisterDTO = new EmployeeRegisterDTO();
        Person person = personService.getPersonByID(id);
        Account account = person.getAccount();

        Address address = person.getAddress().iterator().next();
        Employee employee = person.getEmployee();

        employeeRegisterDTO.setPerson(person);
        employeeRegisterDTO.setAccount(account);
        employeeRegisterDTO.setAddress(address);
        employeeRegisterDTO.setEmployee(employee);

        model.addAttribute("person", employeeRegisterDTO);

        return "hotel_admin_employee_edit";

    }

    @PostMapping("/admin/employee/edit/{id}")
    public String employeeSave(Model model, HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id, @ModelAttribute("person") EmployeeRegisterDTO employeeRegisterDTO)
    {
        if (Objects.isNull(session.getAttribute("role")) || !session.getAttribute("role").equals("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", ControllerDefaultMessage.MANAGER_PERMISSIONS);
            return "redirect:/login";
        }

        model.addAttribute("root", "/admin");


        employeeRegisterDTO.getAccount().setPerson(employeeRegisterDTO.getPerson());
        employeeRegisterDTO.getAddress().setPerson(employeeRegisterDTO.getPerson());
        employeeRegisterDTO.getEmployee().setPerson(employeeRegisterDTO.getPerson());

        try {
            accountService.SaveEditedAccount(employeeRegisterDTO.getAccount());
        } catch (EmailAlreadyExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to edit a employee: " + e.getMessage());
            return "redirect:/admin/employee/edit/" + id;
        }

        try {
            personService.saveEditedPerson(employeeRegisterDTO.getPerson());
        } catch (PersonCellphoneAlreadyExists e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to edit a employee: " + e.getMessage());
            return "redirect:/admin/employee/edit/" + id;
        }

        addressService.saveEditedAddress(employeeRegisterDTO.getAddress());
        employeeService.saveEditedEmployee(employeeRegisterDTO.getEmployee());

        redirectAttributes.addFlashAttribute("successMessage", "The employee was successful edited!");
        return "redirect:/admin/employee/edit/" + id;
    }

}
