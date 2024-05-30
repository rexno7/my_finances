package mywebsite.finances.account;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    public List<Account> findAll(List<String> accountFilter) {
        List<Account> accounts = accountRepository.findAll();
        if (accountFilter != null) {
            accounts = accounts.stream()
                .filter(account -> accountFilter.contains(account.getAccountNumber()))
                .toList();
          }
        return accounts;
    } 
}
