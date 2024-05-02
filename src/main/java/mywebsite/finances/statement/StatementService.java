package mywebsite.finances.statement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mywebsite.finances.statement.parser.StatementParser;
import mywebsite.finances.statement.parser.StatementParserFactory;
import mywebsite.finances.transactions.Transaction;
import mywebsite.finances.transactions.TransactionRepository;

@Service
public class StatementService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private StatementParserFactory statementParserFactory;

  public void upload(MultipartFile statement) throws Exception {
    StatementParser parser = statementParserFactory.getStatementParser(statement);
    List<Transaction> transactions = parser.parse(statement);
    transactionRepository.saveAll(transactions);
  }
}
