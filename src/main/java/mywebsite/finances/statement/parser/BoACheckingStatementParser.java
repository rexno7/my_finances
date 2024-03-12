package mywebsite.finances.statement.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mywebsite.finances.account.Account;
import mywebsite.finances.account.AccountRepository;
import mywebsite.finances.transactions.Category;
import mywebsite.finances.transactions.Transaction;

@Service
public class BoACheckingStatementParser implements StatementParser {
  private final static Pattern STATEMENT_VALIDATION_REGEX = Pattern
      .compile("Bank of America Adv Relationship Banking");
  private final SimpleDateFormat SIMPLE_DATE_FORMATER = new SimpleDateFormat("MM/dd/yy");
  private final String ACCOUNT_NUMBER_REGEX = ".*Account number: \\d{4} \\d{4} (\\d{4})";
  private final Pattern BALANCE_PATTERN = Pattern
      .compile("Ending balance on\\s+(.*)\\s+\\$([0-9,.-]+)$");
  private final Pattern TRANSACTION_PATTERN = Pattern
      .compile("(\\d{2}/\\d{2}/\\d{2})\\s+(.*)\\s+([0-9,.-]+)$");
  private final String DEPOSITS_HEADER = "Deposits and other additions";
  private final String WITHDRAWALS_HEADER = "Withdrawals and other subtractions";
  private final String SINGLE_LINE_TRANSACTION = ".*\\d+\\.\\d{2}";
  private final String TRANSACTION_INDICATOR = "\\d{2}/\\d{2}/\\d{2}.*";

  public static final String TYPE = "BoAChecking";

  @Autowired
  private AccountRepository accountRepository;

  public String getType() {
    return TYPE;
  }

  public static boolean canParse(String statementString) {
    Matcher matcher = STATEMENT_VALIDATION_REGEX.matcher(statementString);
    return matcher.find();
  }

  public List<Transaction> parse(String[] statementLines)
      throws NumberFormatException, ParseException {
    List<Transaction> transactions = new ArrayList<Transaction>();
    List<String> statementLinesList = Arrays.asList(statementLines);
    Iterator<String> statementIterator = statementLinesList.iterator();
    String accountNumber = null;
    Date statementDate = null;
    Double endingValue = null;
    while (statementIterator.hasNext()) {
      String line = statementIterator.next();
      if (accountNumber == null && line.matches(ACCOUNT_NUMBER_REGEX)) {
        accountNumber = line.substring(line.length() - 4);
      } else if (endingValue == null) {
        Matcher matcher = BALANCE_PATTERN.matcher(line);
        if (matcher.find()) {
          System.out.println("found");
          statementDate = (new SimpleDateFormat("MMMM dd, yyyy")).parse(matcher.group(1));
          endingValue = Double.parseDouble(matcher.group(2).replace(",", ""));
          Account account = accountRepository.findByAccountNumber(accountNumber);
          if (account == null) {
            account = new Account(accountNumber, "Bank of America", "Adv Relationship Banking");
          }
          account.addToAccountValueHistory(statementDate, endingValue);
          accountRepository.save(account);
        }
      } else if (line.matches(DEPOSITS_HEADER)) {
        transactions.addAll(
            parseTransactions(
                statementIterator,
                "Total deposits and other additions.*",
                accountNumber));
      } else if (line.matches(WITHDRAWALS_HEADER)) {
        transactions.addAll(
            parseTransactions(
                statementIterator,
                "Total withdrawals and other subtractions.*",
                accountNumber));
        break;
      }
    }
    return transactions;
  }

  private List<Transaction> parseTransactions(Iterator<String> statementIterator,
      String endingRegex, String accountNum) throws NumberFormatException, ParseException {
    List<Transaction> transactions = new ArrayList<Transaction>();
    while (statementIterator.hasNext()) {
      String line = statementIterator.next().strip();
      if (line.matches(endingRegex)) {
        return transactions;
      }
      if (line.matches(TRANSACTION_INDICATOR)) {
        if (!line.matches(SINGLE_LINE_TRANSACTION)) {
          line = line + " " + statementIterator.next().strip() + " "
              + statementIterator.next().strip();
        }
        Matcher matcher = TRANSACTION_PATTERN.matcher(line);
        matcher.find();
        transactions.add(
            new Transaction(line, accountNum, Float.parseFloat(matcher.group(3).replace(",", "")),
                matcher.group(2), Category.UNDEFINED, SIMPLE_DATE_FORMATER.parse(matcher.group(1)),
                null, null, null));
        System.out.println(line);
      }
    }
    return transactions;
  }
}
