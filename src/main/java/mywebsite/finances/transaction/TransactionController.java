package mywebsite.finances.transaction;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mywebsite.finances.account.Account;
import mywebsite.finances.account.AccountService;

@Controller
@RequestMapping({ "/finances", "/finances/" })
public class TransactionController {

  // @Autowired
  // private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountService accountService;

  @ModelAttribute("allCategories")
  public List<Category> populateCategories() {
      return Arrays.asList(Category.values());
  }

  @GetMapping("/transactions")
  public String displayTransactions(Model model,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) List<String> accountFilter,
      @RequestParam(defaultValue = "transactionDate,desc") String[] sort,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "50") int size) {

    List<Account> accounts = accountService.findAll(null);
    List<Account> accountsFiltered = accountService.findAll(accountFilter);
    
    String sortField = sort[0];
    String sortDirection = sort[sort.length - 1];
    
    Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Order order = new Order(direction, sortField);
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
    
    Page<Transaction> transactionPage;
    if (keyword == null && accountFilter == null) {
      transactionPage = transactionRepository.findAll(pageable);
    } else if (accountFilter != null) {
      transactionPage = transactionRepository.findByAccountIn(accountsFiltered, pageable);
    } else {
      transactionPage = transactionRepository.findByMerchantContainingIgnoreCase(keyword, pageable);
      model.addAttribute("keyword", keyword);
    }
    
    int totalPages = transactionPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
      .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }
    
    model.addAttribute("accounts", accounts);
    model.addAttribute("accountsFiltered", accountsFiltered);
    model.addAttribute("transactionPage", transactionPage);
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
