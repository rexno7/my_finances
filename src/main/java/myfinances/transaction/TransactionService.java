package myfinances.transaction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public List<Transaction> findAll() {
    return transactionRepository.findAll();
  }

  public List<Transaction> findTransactionsBefore(Date endDate) {
      return transactionRepository.findByTransactionDateBefore(endDate);
  }

  public List<Transaction> findTransactionsAfter(Date startDate) {
      return transactionRepository.findByTransactionDateAfter(startDate);
  }

  public List<Transaction> findTransactionsBetween(Date startDate, Date endDate) {
      return transactionRepository.findByTransactionDateBetween(startDate, endDate);
  }

  public void saveAll(List<Transaction> transactions) {
    transactionRepository.saveAll(transactions);
  }
}
