package mywebsite.finances.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleRestController {
    
    @Autowired
    private RuleService ruleService;

    @PostMapping("/finances/rule/new")
    public String saveNewRule(Rule newRule) {
        ruleService.saveNewRule(newRule);
        return "rules";
    }

    @DeleteMapping("/finances/rule/{id}")
    public void deleteRule(@PathVariable Long id) {
        System.out.println(id);
        ruleService.deleteRule(id);
    }

}
