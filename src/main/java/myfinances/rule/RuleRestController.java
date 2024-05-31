package myfinances.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rule")
public class RuleRestController {
    
    @Autowired
    private RuleService ruleService;

    @PostMapping("/new")
    public String saveNewRule(Rule newRule) {
        ruleService.saveNewRule(newRule);
        return "rules";
    }

    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        System.out.println(id);
        ruleService.deleteRule(id);
    }

}
