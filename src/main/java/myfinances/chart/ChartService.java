package myfinances.chart;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
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

    public List<IChartEntry> getChartEntriesBetween(Date startDate, Date endDate) {
        return filterEntries(
            transactionRepository.findAllBetweenDatesAndGroupByMerchant(startDate, endDate));
    }

    public List<IChartEntry> getChartEntriesBefore(Date endDate) {
        return filterEntries(transactionRepository.findAllBeforeDateAndGroupByMerchant(endDate));
    }

    public List<IChartEntry> getChartEntriesAfter(Date startDate) {
        return filterEntries(transactionRepository.findAllAfterDateAndGroupByMerchant(startDate));
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

    private List<IChartEntry> filterEntries(List<IChartEntry> entries) {
        return entries.stream()
                .filter(entry -> entry.getAmount() > 0 && Category.TRANSFER != entry.getCategory())
                .toList();
    }
}
