package mywebsite.finances.account;

import java.io.Serializable;
import java.util.Date;

public class AccountBalanceId implements Serializable {
    private Account account;

    private Date statementDate;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((statementDate == null) ? 0 : statementDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountBalanceId other = (AccountBalanceId) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (statementDate == null) {
            if (other.statementDate != null)
                return false;
        } else if (!statementDate.equals(other.statementDate))
            return false;
        return true;
    }
}
