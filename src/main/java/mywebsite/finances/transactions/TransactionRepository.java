package mywebsite.finances.transactions;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAll(Sort sort);

  Page<Transaction> findByMerchantContainingIgnoreCase(String keyword, Pageable pageable);
}
