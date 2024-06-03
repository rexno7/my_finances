package myfinances.transaction;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import myfinances.account.Account;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Page<Transaction> findByMerchantContainingIgnoreCase(String keyword, Pageable pageable);

  Page<Transaction> findByAccountIn(List<Account> accounts, Pageable pageable);

  List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);

  List<Transaction> findByTransactionDateBefore(Date endDate);

  List<Transaction> findByTransactionDateAfter(Date startDate);
}
