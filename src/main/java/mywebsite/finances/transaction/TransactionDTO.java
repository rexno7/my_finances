package mywebsite.finances.transaction;

import java.util.Date;

public class TransactionDTO {
    private Long id;
    private String accountName;
    private String merchant;
    private float amount;
    private String category;
    private Date transactionDate;

    public TransactionDTO(Long id, String accountName, String merchant, float amount,
            String category, Date transactionDate) {
        this.id = id;
        this.accountName = accountName;
        this.merchant = merchant;
        this.amount = amount;
        this.category = category;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}