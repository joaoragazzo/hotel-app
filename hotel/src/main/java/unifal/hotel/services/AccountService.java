package unifal.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unifal.hotel.entity.Account;
import unifal.hotel.exceptions.InvalidAccountCredentials;
import unifal.hotel.repository.jparepository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService
{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(String email, String password_hash) {
        Optional<Account> account =  accountRepository.getAccountByEmailAndPassword(email, password_hash);

        if(account.isPresent()) {
            return account.get();
        }

        throw new InvalidAccountCredentials("Invalid email or password.");

    }

}
