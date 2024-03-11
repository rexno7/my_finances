package mywebsite.finances.statement.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import mywebsite.finances.transactions.Transaction;

public interface StatementParser {

  public final SimpleDateFormat DAY_MONTH_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

  String getType();

  List<Transaction> parse(String[] statementLines) throws IOException, ParseException;
}
