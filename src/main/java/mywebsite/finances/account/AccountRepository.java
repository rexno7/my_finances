package mywebsite.finances.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByAccountNumber(String accountNumber);

  List<Account> findByAccountNumberIn(String[] accountNumbers);
}
