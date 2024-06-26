package myfinances.transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import myfinances.category.CategoryService;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/update")
    public String updateTransaction(@RequestBody UpdateRequest updateRequest) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(updateRequest.getId());
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            switch (updateRequest.getField()) {
                case "date":
                    String[] dateArray = updateRequest.getValue().split("/");
                    int month = Integer.parseInt(dateArray[0]);
                    int day = Integer.parseInt(dateArray[1]);
                    int year = dateArray.length == 3 ? Integer.parseInt(dateArray[2]) : LocalDate.now().getYear();
                    LocalDate localDate = LocalDate.of(year, month, day);
                    transaction.setTransactionDate(Date.from(
                            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    break;
                case "nickname":
                    transaction.setNickname(updateRequest.getValue());
                    break;
                case "amount":
                    transaction.setAmount(Float.parseFloat(updateRequest.getValue()));
                    break;
                case "category":
                    transaction.setCategory(categoryService.findByName(updateRequest.getValue()));
                default:
                    break;
            }
            transactionRepository.save(transaction);
            return "Success";
        }
        return "Transaction not found";
    }

    public static class UpdateRequest {
        private Long id;
        private String field;
        private String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
