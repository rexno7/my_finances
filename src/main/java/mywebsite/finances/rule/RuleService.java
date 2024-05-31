package mywebsite.finances.rule;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    
    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Rule saveNewRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }
}
