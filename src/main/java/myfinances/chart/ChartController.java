package myfinances.chart;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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

    @GetMapping("/")
    public String getTimeFrame(Model model,
            @RequestParam(required = false) String beforeString,
            @RequestParam(required = false) String afterString) {

        Date before = null;
        Date after = null;
        if (beforeString != null && afterString != null) {
            try {
                Integer[] beforeArray = (Integer[]) Arrays.stream(beforeString.split("/"))
                        .map(Integer::parseInt)
                        .toArray();
                Integer[] afterArray = (Integer[]) Arrays.stream(afterString.split("/"))
                .map(Integer::parseInt)
                .toArray();
                before = Date.from(LocalDate.of(beforeArray[2], beforeArray[1], beforeArray[0]).atStartOfDay(ZoneId.systemDefault()).toInstant());
                after = Date.from(LocalDate.of(afterArray[2], afterArray[1], afterArray[0]).atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        if (before == null || after == null) {
            before = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            after = Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        
        List<IChartEntry> entries = chartService.getChartEntriesBetween(before, after);
        model.addAttribute("transactions", entries);
        return "merchants-chart-view";
    }
    
    @GetMapping("/merchants")
    public String getChartOfMerchantsForGivenMonth(Model model, 
            @RequestParam(required = false) Integer year, 
            @RequestParam(required = false) Integer month) {
        try {
            List<IChartEntry> entries = null;
            LocalDate now = LocalDate.now();
            if (year == null) {
                year = now.getYear();
            }
            if (month == null) {
                month = now.getMonthValue();
            }
            entries = chartService.getMonthChartEntries(year, month);
            model.addAttribute("chartEntries", entries);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return "merchants-chart-view";
    }
}
