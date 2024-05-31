package myfinances.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/statements")
public class StatementController {

  @Autowired
  private StatementService statementService;

  @GetMapping("/upload")
  public String displayUploadFilePage(Model model) {
    return "upload-statement";
  }

  @PostMapping("/upload")
  public String upload(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) throws Exception {
    statementService.upload(file);
    return "redirect:/statements/upload";
  }
}
