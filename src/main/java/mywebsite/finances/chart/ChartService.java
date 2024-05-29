package mywebsite.finances.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mywebsite.finances.transactions.Transaction;
import mywebsite.finances.transactions.TransactionRepository;

@Service
public class ChartService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDTO> getAllTransactions() {
        return convertListToTransactionDTO(transactionRepository.findAll());
    }

    public List<TransactionDTO> getCurrentMonthTransactions() throws ParseException {
        LocalDate currentDate = LocalDate.now();
        return getMonthTransactions(currentDate.getYear(), currentDate.getMonthValue());
    }

    public List<TransactionDTO> getLastMonthsTransactions() throws ParseException {
        LocalDate lastMonth = LocalDate.now().minusMonths(1L);
        return getMonthTransactions(lastMonth.getYear(), lastMonth.getMonthValue());
    }

    public List<TransactionDTO> getMonthTransactions(int year, int month) throws ParseException {
        int lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth().getDayOfMonth();
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/01/" + year);
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/" +lastDayOfMonth + "/" + year);
        return convertFilterAndSort(
                transactionRepository.findByTransactionDateBetween(startDate, endDate));
    }

    private List<TransactionDTO> convertFilterAndSort(List<Transaction> transactionList) {
        return convertListToTransactionDTO(transactionList)
                .stream()
                .filter(txn -> txn.getAmount() > 0)
                .sorted(Comparator.comparing(TransactionDTO::getAmount))
                .toList();
    }

    private List<TransactionDTO> convertListToTransactionDTO(List<Transaction> transactionList) {
        return transactionList.stream().map(txn -> new TransactionDTO(
                txn.getAccount().getName(),
                txn.getNickname() == null ? txn.getMerchant() : txn.getNickname(),
                txn.getAmount(),
                txn.getCategory().toString(),
                txn.getTransactionDate()))
                .toList();
    }
}
