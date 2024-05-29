package mywebsite.finances.chart;

import java.util.Date;

public class TransactionDTO {
    private String accountName;
    private String merchant;
    private float amount;
    private String category;
    private Date transactionDate;

    public TransactionDTO(String accountName, String merchant, float amount, String category, Date transactionDate) {
        this.accountName = accountName;
        this.merchant = merchant;
        this.amount = amount;
        this.category = category;
        this.transactionDate = transactionDate;
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