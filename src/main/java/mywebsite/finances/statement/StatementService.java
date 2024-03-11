package mywebsite.finances.statement;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mywebsite.finances.statement.parser.BoACCStatementParser;
import mywebsite.finances.statement.parser.BoACheckingStatementParser;
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

  public void uploadStatementFile(MultipartFile statementFile) throws Exception {
    String statementString = convertFileToString(statementFile);
    List<Transaction> transactions = null;
    try {
      transactions = findStatementParser(statementString)
          .parse(statementString.split(System.lineSeparator()));
    } catch (Exception e) {
      throw new Exception(statementFile.getOriginalFilename() + " could not be parsed.", e);
    }
    transactionRepository.saveAll(transactions);
  }

  public <T> StatementParser findStatementParser(String statementString) throws Exception {
    if (BoACCStatementParser.canParse(statementString)) {
      return statementParserFactory.getParser(BoACCStatementParser.TYPE);
    } else if (BoACheckingStatementParser.canParse(statementString)) {
      return statementParserFactory.getParser(BoACheckingStatementParser.TYPE);
    }
    throw new Exception("No statement parser found.");
  }

  private String convertFileToString(MultipartFile file) throws IOException {
    PDDocument document = PDDocument.load(file.getInputStream());
    PDFTextStripper stripper = new PDFTextStripper();
    String pdfText = stripper.getText(document);
    document.close();
    return pdfText;
  }
}
