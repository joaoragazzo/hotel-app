package unifal.hotel.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Account;
import unifal.hotel.exceptions.EmailAlreadyExists;
import unifal.hotel.exceptions.InvalidAccountCredentials;
import unifal.hotel.repository.jparepository.AccountRepository;
import unifal.hotel.utils.Security;

import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class AccountService
{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account loginAccount(String email, String password_hash) {
        Optional<Account> account =  accountRepository.getAccountByEmailAndPassword(email, password_hash);

        if(account.isPresent()) {
            return account.get();
        }

        throw new InvalidAccountCredentials("Invalid email or password.");

    }

    public Account findAccountById(Long Id) {
        Optional<Account> account = accountRepository.findById(Id);

        return account.orElse(null);
    }

    public boolean checkIfEmailExists(String email) {
        return accountRepository.existsByEmail(email);
    }
    public void SaveEditedAccount(Account account) {

        String actual_email = findAccountById(account.getId()).getEmail();
        String actual_hash_password = findAccountById(account.getId()).getPassword();


        if(account.getPassword() != "")
            account.setPassword(Security.encryptPasswordSHA256(account.getPassword()));
        else
            account.setPassword(actual_hash_password);

        if (actual_email.equals(account.getEmail())) {
            accountRepository.save(account);
            return;
        }

        if (!checkIfEmailExists(account.getEmail())) {
            accountRepository.save(account);
            return;
        }

        throw new EmailAlreadyExists("This email already is registered into our database.");
    }

}
