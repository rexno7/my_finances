package mywebsite.finances.transactions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({ "/finances", "/finances/" })
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;

//  @GetMapping("/transactions")
//  public String getTransactions(Model model) {
//    model.addAttribute("transactions", transactionService.list());
//    return "transactions";
//  }

  @GetMapping("/transactions")
  public String listTransactions(Model model, @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "transactionDate,desc") String[] sort,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int size) {
    String sortField = sort[0];
    String sortDirection = sort[1];

    Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Order order = new Order(direction, sortField);

    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

    Page<Transaction> transactionPage;
    if (keyword == null) {
      transactionPage = transactionRepository.findAll(pageable);
    } else {
      transactionPage = transactionRepository.findByMerchantContainingIgnoreCase(keyword, pageable);
      model.addAttribute("keyword", keyword);
    }

    model.addAttribute("transactionPage", transactionPage);

    int totalPages = transactionPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    model.addAttribute("keyword", keyword);
    model.addAttribute("currentPage", transactionPage.getNumber() + 1);
    model.addAttribute("totalItems", transactionPage.getTotalElements());
    model.addAttribute("totalPages", transactionPage.getTotalPages());
    model.addAttribute("pageSize", size);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDirection", sortDirection);
    model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

    return "transactions";
  }

}
