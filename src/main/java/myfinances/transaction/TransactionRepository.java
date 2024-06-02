package myfinances.transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import myfinances.account.Account;
import myfinances.chart.IChartEntry;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);

  Page<Transaction> findByMerchantContainingIgnoreCase(String keyword, Pageable pageable);

  Page<Transaction> findByAccountIn(List<Account> accounts, Pageable pageable);

  @Query("""
            SELECT merchant AS merchant, category AS category, SUM(amount) AS amount
            FROM Transaction
            WHERE transactionDate BETWEEN ?1 AND ?2
            GROUP BY merchant, category
            ORDER BY amount
            """)
  List<IChartEntry> findAllBetweenDatesAndGroupByMerchant(Date startDate, Date endDate);

  @Query("""
            SELECT merchant AS merchant, category AS category, SUM(amount) AS amount
            FROM Transaction
            WHERE transactionDate BETWEEN ?1 AND ?2
            GROUP BY merchant, category
            ORDER BY amount
            """)
  List<IChartEntry> findAllBetweenDatesAndGroupByMerchant(LocalDate before, LocalDate after);
}
