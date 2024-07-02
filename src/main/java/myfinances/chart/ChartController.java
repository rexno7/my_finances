package myfinances.chart;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myfinances.transaction.Transaction;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping()
    public String getTransactionChart(Model model,
            @RequestParam(required = false) String before,
            @RequestParam(required = false) String after,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        List<Transaction> entries;
        if (before != null || after != null) {
            entries = chartService.getTransactionsByTimeRange(before, after);
        } else if (month != null) {
            entries = chartService.getTransactionsByMonth(year == null ? LocalDate.now().getYear() : year, month);
        } else {
            entries = chartService.geTransactionsByCurrentMonth();
        }

        model.addAttribute("spendingText", generateSpendingText(before, after, year, month));
        model.addAttribute("transactions", entries);
        return "transaction-chart";
    }

    private String generateSpendingText(String before, String after, Integer year, Integer month) {
        if (before != null && after != null) {
            return "Spending for " + after + " - " + before;
        } else if (before != null) {
            return "Spending before " + before;
        } else if (after != null) {
            return "Spending after " + after;
        } else if (month != null) {
            LocalDate now = LocalDate.now();
            return "Spending for " + (LocalDate.of(now.getYear(), month, 1).getMonth().name()) + " "
                    + (year != null ? year : now.getYear());
        }
        LocalDate now = LocalDate.now();
        return "Spending for " + now.getMonthValue() + "/" + now.getYear();
    }
}
