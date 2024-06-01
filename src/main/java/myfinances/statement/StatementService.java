package myfinances.statement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import myfinances.rule.RuleService;
import myfinances.transaction.Transaction;
import myfinances.transaction.TransactionRepository;

@Service
public class StatementService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private StatementParserFactory statementParserFactory;

  @Autowired
  private RuleService ruleService;

  public void upload(MultipartFile statement) throws Exception {
    StatementParser parser = statementParserFactory.getStatementParser(statement);
    List<Transaction> transactions = parser.parse(statement);
    ruleService.executeRules(transactions);
    transactionRepository.saveAll(transactions);
  }
}
