package unifal.hotel.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import unifal.hotel.entity.Account;
import unifal.hotel.exceptions.InvalidAccountCredentials;
import unifal.hotel.services.AccountService;
import unifal.hotel.utils.Security;

import java.util.Objects;

@Log4j2
@Controller
@AllArgsConstructor
public class SessionController {

    private final AccountService accountService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("hotel_login");

        Account account = new Account();
        mv.addObject("account", account);

        return mv;
    }


    @PostMapping("/login")
    public String authentication(RedirectAttributes redirectAttributes, Account account, HttpSession session) {

        String email = account.getEmail();
        String password = account.getPassword();
        Account account_response = null;

        String password_hash = Security.encryptPasswordSHA256(password);

        try {
            account_response = accountService.loginAccount(email, password_hash);
        } catch (InvalidAccountCredentials e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to login: " + e.getMessage());
            return "redirect:/login";
        }

        if (Objects.isNull(account_response)) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying to login: Unknown error");
            return "redirect:/login";
        }

        if (Objects.isNull(account_response.getPerson().getEmployee())) {
            redirectAttributes.addFlashAttribute("errorMessage", "A error happened when trying " +
                    "  to login: This employee do not have permission enter this area.");
            return "redirect:/login";
        }

        if (account_response.getPerson().getEmployee().isReceptionist()) {
            session.setAttribute("role", "receptionist");
            return "redirect:/home";
        }


        session.setAttribute("role", "admin");
        return "redirect:/admin";
    }


    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes, HttpSession session) {

        if (Objects.isNull(session.getAttribute("role"))) {
            redirectAttributes.addFlashAttribute("errorMessage", "You need to login to be able to logout.");
            return "redirect:/login";
        }

        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "You was successful logout.");

        return "redirect:/login";
    }
}
