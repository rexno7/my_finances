package mywebsite.finances.statement.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class StatementParserFactory {

  @Autowired
  private List<StatementParser> statementParsers;

  private static final Map<String, StatementParser> myStatementParserCache = new HashMap<>();

  @PostConstruct
  public void initMyStatementParserCache() {
    for (StatementParser statementParser : statementParsers) {
      System.out.println(statementParser.getType());
      myStatementParserCache.put(statementParser.getType(), statementParser);
    }
  }

  public StatementParser getParser(String type) {
    StatementParser statementParser = myStatementParserCache.get(type);
    if (statementParser == null) {
      throw new RuntimeException("Unknown statement parser type: " + type);
    }
    return statementParser;
  }
}
