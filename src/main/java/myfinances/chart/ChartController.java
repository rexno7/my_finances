package myfinances.chart;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
            @RequestParam(required = false) String after) {
        Date startDate = null;
        Date endDate = null;
        try {
            if (after != null) {
                startDate = chartService.StringToDate(after);
            }
            if (before != null) {
                endDate = chartService.StringToDate(before);
            }
        } catch (NumberFormatException e) {
            startDate = null;
            endDate = null;
        }
        if (startDate == null && endDate == null) {
            startDate = Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            endDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        List<Transaction> entries;
        if (endDate == null) {
            entries = chartService.getTransactionsAfter(startDate);
        } else if (startDate == null) {
            entries = chartService.getTransactionsBefore(endDate);
        } else {
            entries = chartService.getTransactionsBetween(startDate, endDate);
        }
        model.addAttribute("transactions", entries);
        return "transaction-chart";
    }
}
