package myfinances.rule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import myfinances.transaction.Category;
import myfinances.transaction.Transaction;

@Entity
public class Rule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @NotBlank
    private String ruleRegex;

    @NotBlank
    private String executionString;

    @NotBlank
    private String ruleRunOn;

    @NotBlank
    private String executedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleRegex() {
        return ruleRegex;
    }

    public void setRuleRegex(String ruleRegex) {
        this.ruleRegex = ruleRegex;
    }

    public String getExecutionString() {
        return executionString;
    }

    public void setExecutionString(String executionString) {
        this.executionString = executionString;
    }

    public String getRuleRunOn() {
        return ruleRunOn;
    }

    public void setRuleRunOn(String ruleRunOn) {
        this.ruleRunOn = ruleRunOn;
    }

    public String getExecutedOn() {
        return executedOn;
    }

    public void setExecutedOn(String executedOn) {
        this.executedOn = executedOn;
    }

    public void runOn(Transaction transaction) {
        String transactionRunOnString = null;
        if (ruleRunOn.equals("merchant")) {
            transactionRunOnString = transaction.getMerchant();
        } else if (ruleRunOn.equals("category")) {
            transactionRunOnString = transaction.getCategory().name();
        } else {
            return;
        }
        if (transactionRunOnString.matches(ruleRegex)) {
            if (executedOn.equals("merchant")) {
                transaction.setNickname(executionString);
            } else if (executedOn.equals("category")) {
                transaction.setCategory(Category.valueOf(executionString));
            }
        }
    }
}
