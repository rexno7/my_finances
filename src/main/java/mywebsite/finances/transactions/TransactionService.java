package mywebsite.finances.transactions;

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
//    sortList(sortParams, transactions);
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
    String[] sortParamList = sortParams.split(",");
    int modifier = 1;
    if (sortParamList.length > 0
        && "desc".equalsIgnoreCase(sortParamList[sortParamList.length - 1])) {
      modifier = -1;
    }
    for (String sortName : sortParamList) {
      switch (sortName) {
        case ("merchant"): {
          list.sort((t1, t2) -> t1.getMerchant().compareTo(t2.getMerchant()));
          list.stream().sorted(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
              return t1.getMerchant().compareTo(t2.getMerchant());
            }
          });
          break;
        }
        case ("date"): {
          list.sort((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
          break;
        }
        case ("acctNo"): {
          list.sort((t1, t2) -> t1.getAccountNum().compareTo(t2.getAccountNum()));
          break;
        }
        case ("amount"): {
          list.sort((t1, t2) -> (int) (t1.getAmount() - t2.getAmount()));
          break;
        }
      }
    }
  }
}
