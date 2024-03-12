package mywebsite.finances.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByAccountNumber(String accountNumber);
}
