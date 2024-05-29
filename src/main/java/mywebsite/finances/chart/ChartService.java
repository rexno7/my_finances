package mywebsite.finances.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getCurrentMonthTransactions() throws ParseException {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/01/" + year);
        Date endDate = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<Transaction> getLastMonthsTransactions() throws ParseException {
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2024");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("07/01/2024");
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public static List<TransactionDTO> convertListToTransactionDTO(List<Transaction> transactionList) {
        return transactionList.stream().map(txn -> new TransactionDTO(
                txn.getAccount().getName(),
                txn.getNickname() == null ? txn.getMerchant() : txn.getNickname(),
                txn.getAmount(),
                txn.getCategory().toString(),
                txn.getTransactionDate()))
                .sorted(Comparator.comparing(TransactionDTO::getAmount).reversed())
                .toList();
    }

}
 