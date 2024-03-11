package mywebsite.finances.statement.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import mywebsite.finances.transactions.Category;
import mywebsite.finances.transactions.Transaction;

@Service
public class BoACCStatementParser implements StatementParser {
  private final static Pattern STATEMENT_VALIDATION_REGEX = Pattern
      .compile("www.bankofamerica.com.*Visa Signature", Pattern.DOTALL);
  private final Pattern TRANSACTION_REGEX = Pattern.compile(
      "(\\d{2}\\/\\d{2})\\s+(\\d{2}\\/\\d{2})\\s+(.*?)\\s?(\\d{4})?\\s?(\\d{4})?\\s+([0-9,.-]+)$");
  private final Pattern STATEMENT_PERIOD_REGEX = Pattern
      .compile("(\\w+)\\s\\d+\\s-\\s(\\w+)\\s\\d+,\\s(\\d{4})");

  public static final String TYPE = "BoACC";

  public String getType() {
    return TYPE;
  }

  public static boolean canParse(String statementString) {
    Matcher matcher = STATEMENT_VALIDATION_REGEX.matcher(statementString);
    return matcher.find();
  }

  public List<Transaction> parse(String[] statementLines) throws IOException, ParseException {
    String statementPeriod = null;
    String boaAccountNumber = null;
    List<Transaction> transactions = new ArrayList<Transaction>();
    for (String line : statementLines) {
      if (statementPeriod == null && STATEMENT_PERIOD_REGEX.matcher(line).find()) {
        statementPeriod = line;
      }
      Matcher transactionMatcher = TRANSACTION_REGEX.matcher(line);
      if (transactionMatcher.find()) {
        String transactionDate = transactionMatcher.group(1);
        String postingDate = transactionMatcher.group(2);
        String merchant = transactionMatcher.group(3).replaceAll("\\s+", " ");
        String referenceNum = transactionMatcher.group(4);
        String accountNum = transactionMatcher.group(5);
        Float amount = Float.parseFloat(transactionMatcher.group(6).replace(",", ""));
        if (accountNum == null) {
          referenceNum = null;
          accountNum = boaAccountNumber;
        } else if (boaAccountNumber == null) {
          boaAccountNumber = transactionMatcher.group(5);
        }
        transactions.add(
            new Transaction(line, accountNum, amount, merchant, Category.UNDEFINED,
                calcTransactionDate(statementPeriod, transactionDate),
                calcTransactionDate(statementPeriod, postingDate), null, referenceNum));
      }
    }

    return transactions;
  }

  private Date calcTransactionDate(String statementPeriod, String monthAndDate)
      throws ParseException {
    boolean isInDecember = "12".equals(monthAndDate.substring(0, 2));
    Matcher matcher = STATEMENT_PERIOD_REGEX.matcher(statementPeriod);
    String year = statementPeriod.substring(statementPeriod.length() - 4);
    if (isInDecember && matcher.find()) {
      String startingMonth = matcher.group(1);
      String endingMonth = matcher.group(2);
      if (isInDecember && "December".equals(startingMonth) && "January".equals(endingMonth)) {
        year = (Integer.parseInt(year) - 1) + "";
      }
    }
    return DAY_MONTH_FORMATTER.parse(monthAndDate + "/" + year);
  }
}
