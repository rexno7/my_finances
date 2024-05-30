package mywebsite.finances.transaction;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mywebsite.finances.account.Account;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);

  Page<Transaction> findByMerchantContainingIgnoreCase(String keyword, Pageable pageable);

  Page<Transaction> findByAccountIn(List<Account> accounts, Pageable pageable);
}
