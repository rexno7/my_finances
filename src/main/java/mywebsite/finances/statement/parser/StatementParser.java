package mywebsite.finances.statement.parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import mywebsite.finances.transactions.Transaction;

public interface StatementParser {

  public final SimpleDateFormat DAY_MONTH_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

  public boolean canParse(MultipartFile multipartFile);

  List<Transaction> parse(MultipartFile multipartFile) throws Exception;

  public static String convertFileToString(MultipartFile file) throws IOException {
    PDDocument document = PDDocument.load(file.getInputStream());
    PDFTextStripper stripper = new PDFTextStripper();
    String pdfText = stripper.getText(document);
    document.close();
    return pdfText;
  }
}
