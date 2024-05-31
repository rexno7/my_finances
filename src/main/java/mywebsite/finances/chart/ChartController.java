package mywebsite.finances.chart;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/merchants")
    public String getChartOfMerchantsForGivenMonth(Model model, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        try {
            List<IChartEntry> entries = null;
            if (year == null || month == null) {
                LocalDate now = LocalDate.now();
                entries = chartService.getMonthChartEntries(now.getYear(), now.getMonthValue());
            } else {
                entries = chartService.getMonthChartEntries(year, month);
            }
            model.addAttribute("chartEntries", entries);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return "merchants-chart-view";
    }
}
