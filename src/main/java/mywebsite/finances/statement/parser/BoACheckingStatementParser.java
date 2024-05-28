package mywebsite.finances.statement.parser;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import mywebsite.finances.account.Account;
import mywebsite.finances.account.AccountBalance;
import mywebsite.finances.account.AccountRepository;
import mywebsite.finances.account.AccountBalanceRepository;
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

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountBalanceRepository accountBalanceRepository;

  @Override
  public boolean canParse(MultipartFile file) {
    String statementString;
    try {
      statementString = StatementParser.convertFileToString(file);
    } catch (IOException e) {
      // TODO: log error
      return false;
    }
    Matcher matcher = STATEMENT_VALIDATION_REGEX.matcher(statementString);
    return matcher.find();
  }

  public List<Transaction> parse(MultipartFile file) throws Exception {
    String[] statementLines = StatementParser.convertFileToString(file).split(System.lineSeparator());
    List<Transaction> transactions = new ArrayList<Transaction>();
    List<String> statementLinesList = Arrays.asList(statementLines);
    Iterator<String> statementIterator = statementLinesList.iterator();
    Account account = null;
    Date statementDate = null;
    Double statementEndValue = null;
    while (statementIterator.hasNext()) {
      String line = statementIterator.next();
      if (account == null && line.matches(ACCOUNT_NUMBER_REGEX)) {
        String accountNumber = line.substring(line.length() - 4);
        account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
          account = new Account(accountNumber, "Bank of America", "Adv Relationship Banking");
          accountRepository.save(account);
        }
      } else if (statementEndValue == null) {
        Matcher matcher = BALANCE_PATTERN.matcher(line);
        if (matcher.find()) {
          statementDate = (new SimpleDateFormat("MMMM dd, yyyy")).parse(matcher.group(1));
          statementEndValue = Double.parseDouble(matcher.group(2).replace(",", ""));
          AccountBalance accountValue = new AccountBalance(account, statementDate, statementEndValue);
          accountBalanceRepository.save(accountValue);
          accountRepository.save(account);
        }
      } else if (line.matches(DEPOSITS_HEADER)) {
        transactions.addAll(
            parseTransactions(statementIterator, "Total deposits and other additions.*", account));
      } else if (line.matches(WITHDRAWALS_HEADER)) {
        transactions.addAll(
            parseTransactions(
                statementIterator,
                "Total withdrawals and other subtractions.*",
                account));
        break;
      }
    }
    return transactions;
  }

  private List<Transaction> parseTransactions(Iterator<String> statementIterator,
      String endingRegex, Account account) throws NumberFormatException, ParseException {
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
            new Transaction(line, account, Float.parseFloat(matcher.group(3).replace(",", "")),
                matcher.group(2), Category.UNDEFINED, SIMPLE_DATE_FORMATER.parse(matcher.group(1)),
                null, null, null));
      }
    }
    return transactions;
  }
}
