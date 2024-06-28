package myfinances.chart;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myfinances.transaction.Transaction;
import myfinances.transaction.TransactionService;

@Service
public class ChartService {

    @Autowired
    private TransactionService transactionService;

    public List<Transaction> geTransactionsByCurrentMonth() {
        LocalDate now = LocalDate.now();
        return getTransactionsByMonth(now.getYear(), now.getMonthValue());
    }

    public List<Transaction> getTransactionsByMonth(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
        return filterTransactions(transactionService.findTransactionsBetween(
            Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

    public List<Transaction> getTransactionsByTimeRange(String before, String after) {
        Date startDate = null;
        Date endDate = null;
        try {
            if (after != null) {
                startDate = stringToDate(after);
            }
            if (before != null) {
                endDate = stringToDate(before);
            }
        } catch (NumberFormatException e) {
            startDate = null;
            endDate = null;
        }
        if (startDate == null && endDate == null) {
            startDate = Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            endDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if (endDate == null) {
            return filterTransactions(transactionService.findTransactionsAfter(startDate));
        } else if (startDate == null) {
            return filterTransactions(transactionService.findTransactionsBefore(endDate));
        } 
        return filterTransactions(transactionService.findTransactionsBetween(startDate, endDate));
    }
    
        private List<Transaction> filterTransactions(List<Transaction> transactions) {
            return transactions.stream()
                    .filter(transaction -> {
                        return transaction.getAmount() != 0 && (transaction.getCategory() == null
                                || transaction.getCategory().isUsedInCalculations());
                    })
                    .toList();
        }

    private Date stringToDate(String dateString) throws NumberFormatException {
        List<Integer> endList = Arrays.stream(dateString.split("/"))
                .map(Integer::parseInt)
                .toList();
        int year = endList.get(2) < 1000 ? endList.get(2) + 2000 : endList.get(2);
        int month = endList.get(0);
        int day = endList.get(1);
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
