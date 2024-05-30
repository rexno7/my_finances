package mywebsite.finances.statement;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StatementParserFactory {

  @Autowired
  private List<StatementParser> statementParsers;

  public StatementParser getStatementParser(MultipartFile multipartFile) throws IOException{
    for (StatementParser parser : statementParsers) {
      if (parser.canParse(multipartFile)) {
        return parser;
      }
    }
    return null;
  }
}
