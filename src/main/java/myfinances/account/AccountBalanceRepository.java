package myfinances.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, AccountBalanceId> {
    
}
