package mywebsite.finances.transaction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  public Transaction save(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public TransactionDTO update(TransactionDTO transactionDTO) {
    Transaction existingTransaction =
        transactionRepository.findById(transactionDTO.getId()).get();
    existingTransaction.setNickname(transactionDTO.getMerchant());
    existingTransaction.setCategory(Category.valueOf(transactionDTO.getCategory()));
    existingTransaction.setTransactionDate(transactionDTO.getTransactionDate());
    transactionRepository.save(existingTransaction);
    return transactionDTO;
  }

  public List<Transaction> list() {
    List<Transaction> transactions = transactionRepository.findAll();
    transactions.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
    return transactions;
  }

  public Page<Transaction> findPaginated(Pageable pageable) {
    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;
    List<Transaction> list;

    List<Transaction> transactions = list();
    // sortList(sortParams, transactions);
    if (transactions.size() < startItem) {
      list = Collections.emptyList();
    } else {
      int toIndex = Math.min(startItem + pageSize, transactions.size());
      list = transactions.subList(startItem, toIndex);
    }

    Page<Transaction> transactionPage = new PageImpl<Transaction>(list,
        PageRequest.of(currentPage, pageSize), transactions.size());

    return transactionPage;
  }

  public void sortList(String sortParams, List<Transaction> list) {
    String[] sortParamList = sortParams.toLowerCase().split(",");

    Comparator<Transaction> merchantComparator = Comparator.comparing(Transaction::getMerchant);
    Comparator<Transaction> dateComparator = Comparator.comparing(Transaction::getTransactionDate);
    Comparator<Transaction> amountComparator = Comparator.comparing(Transaction::getAmount);
    Comparator<Transaction> accountComparator = (Transaction a, Transaction b) -> a.getAccount().getAccountNumber()
        .compareTo(b.getAccount().getAccountNumber());

    Comparator<Transaction> chain = null;
    for (String sortName : sortParamList) {
      if ("merchant".equals(sortName)) {
        chain = chainComparator(chain, merchantComparator);
      } else if ("date".equals(sortName)) {
        chain = chainComparator(chain, dateComparator);
      } else if ("acctNo".equals(sortName)) {
        chain = chainComparator(chain, accountComparator);
      } else if ("amount".equals(sortName)) {
        chain = chainComparator(chain, amountComparator);
      }
    }
    list.sort(chain);
  }

  private Comparator<Transaction> chainComparator(Comparator<Transaction> chain,
      Comparator<Transaction> additionalComparator) {
    if (chain == null) {
      return additionalComparator;
    }
    return chain.thenComparing(additionalComparator);
  }
}
