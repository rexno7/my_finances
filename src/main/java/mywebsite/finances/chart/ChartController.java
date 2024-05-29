package mywebsite.finances.chart;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import mywebsite.finances.transactions.Transaction;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/thisMonth")
    public String getChartForThisMonth(Model model) {
        try {
            List<Transaction> lastMonthsTransactions = chartService.getCurrentMonthTransactions();
            List<TransactionDTO> lastMonthsTransactionDTOs = ChartService
                    .convertListToTransactionDTO(lastMonthsTransactions);
            model.addAttribute("transactions", lastMonthsTransactionDTOs);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return "chart-view";
    }
}
