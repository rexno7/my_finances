package myfinances.chart;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myfinances.transaction.Category;
import myfinances.transaction.Transaction;
import myfinances.transaction.TransactionRepository;

@Service
public class ChartService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsBefore(Date endDate) {
        return filterTransactions(transactionRepository.findByTransactionDateBefore(endDate));
    }

    public List<Transaction> getTransactionsAfter(Date startDate) {
        return filterTransactions(transactionRepository.findByTransactionDateAfter(startDate));
    }

    public List<Transaction> getTransactionsBetween(Date startDate, Date endDate) {
        return filterTransactions(transactionRepository.findByTransactionDateBetween(startDate, endDate));
    }

    public Date StringToDate(String dateString) throws NumberFormatException {
        List<Integer> endList = Arrays.stream(dateString.split("/"))
                .map(Integer::parseInt)
                .toList();
        int year = endList.get(2) < 1000 ? endList.get(2) + 2000 : endList.get(2);
        int month = endList.get(0);
        int day = endList.get(1);
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private List<Transaction> filterTransactions(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> {
                    return transaction.getAmount() != 0 && Category.TRANSFER != transaction.getCategory();
                })
                .toList();
    }
}
