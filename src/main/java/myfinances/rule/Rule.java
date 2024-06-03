package myfinances.rule;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import myfinances.category.Category;
import myfinances.transaction.Transaction;

@Entity
public class Rule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String ruleRegex;

    private String updatedNickname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category updateToCategory;

    private boolean ignoreCase = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleRegex() {
        return ruleRegex;
    }

    public void setRuleRegex(String ruleRegex) {
        this.ruleRegex = ruleRegex;
    }

    public String getUpdatedNickname() {
        return updatedNickname;
    }

    public void setUpdatedNickname(String updatedNickname) {
        this.updatedNickname = updatedNickname;
    }

    public Category getUpdateToCategory() {
        return updateToCategory;
    }

    public void setUpdateToCategory(Category updateToCategory) {
        this.updateToCategory = updateToCategory;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void runOn(Transaction transaction) {
        String fullRegex = "(?i).*" + ruleRegex + ".*";
        if (transaction.getMerchant().matches(fullRegex)) {
            if (updatedNickname != null && !updatedNickname.isBlank()) {
                transaction.setNickname(updatedNickname);
            }
            if (updateToCategory != null) {
                transaction.setCategory(updateToCategory);
            }
        }
    }
}
