package mywebsite.finances.account;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
@IdClass(AccountBalanceId.class)
public class AccountBalance {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Id
    private Date statementDate;

    @NotNull
    private double balance;

    public AccountBalance() {
        
    }

    public AccountBalance(Account account, Date statementDate, double balance) {
        this.account = account;
        this.statementDate = statementDate;
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(Date statementDate) {
        this.statementDate = statementDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountBalance [account=" + account + ", statementDate=" + statementDate + ", balance=" + balance + "]";
    }
}
