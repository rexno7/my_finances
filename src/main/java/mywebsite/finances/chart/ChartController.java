package mywebsite.finances.chart;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mywebsite.finances.transaction.TransactionDTO;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/month")
    public String getChartForGivenYearMonth(Model model, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        try {
            List<TransactionDTO> transactions = null;
            if (year == null || month == null) {
                transactions = chartService.getCurrentMonthTransactions();
            } else {
                transactions = chartService.getMonthTransactions(year, month);
            }
            model.addAttribute("transactions", transactions);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return "chart-view";
    }
}
