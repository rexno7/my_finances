package myfinances.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myfinances.transaction.Category;
import myfinances.transaction.TransactionRepository;

@Service
public class ChartService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<IChartEntry> getMonthChartEntries(int year, int month) throws ParseException {
        int lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth().getDayOfMonth();
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/01/" + year);
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(month + "/" +lastDayOfMonth + "/" + year);
        return transactionRepository.findAllBetweenDatesAndGroupByMerchant(startDate, endDate)
                .stream()
                .filter(entry -> entry.getAmount() > 0 && Category.TRANSFER != entry.getCategory())
                .toList();
    }
}
