package myfinances.rule;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import myfinances.transaction.Category;

@Controller
@RequestMapping(path = "/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @ModelAttribute("allCategories")
    public List<Category> populateCategories() {
        return Arrays.asList(Category.values());
    }

    @ModelAttribute("allTransactionFields")
    public List<String> populateTransactions() {
        return Arrays.asList("", "Merchant", "Category");
    }

    @ModelAttribute("rules")
    public List<Rule> populateRules() {
        return ruleService.findAll();
    }

    @GetMapping()
    public String displayRules(Model model) {
        model.addAttribute("newRule", new Rule());
        return "rules";
    }

    @PostMapping()
    public String saveNewRule(@Valid final Rule newRule, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newRule", newRule);
            return "rules";
        }
        ruleService.saveNewRule(newRule);
        model.clear();
        return "redirect:/rules";
    }
}