package myfinances.rule;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myfinances.transaction.Transaction;
import myfinances.transaction.TransactionService;

@Service
public class RuleService {
    
    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private TransactionService transactionService;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Rule saveNewRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }

    public void executeRules(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            for (Rule rule : this.findAll()) {
                rule.runOn(transaction);
            }
        }
    }

    public void runAll() {
        List<Transaction> transactions = transactionService.findAll();
        executeRules(transactions);
        transactionService.saveAll(transactions);
    }
}
