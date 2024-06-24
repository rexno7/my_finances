package myfinances.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("allCategories")
    public List<Category> populateCategories() {
        return categoryService.findAll();
    }

    @GetMapping()
    public String getCategoryPage(Model model) {
        model.addAttribute("newCategory", new Category());
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @PostMapping("/add")
    public String postMethodName(@Valid final Category newCategory, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            model.addAttribute("newRule", newCategory);
            return "categories";
        }
        categoryService.save(newCategory);
        model.clear();
        return "redirect:/categories";
    }
    
    @PostMapping("/delete/{id}")
    public String postMethodName(@PathVariable String id) {
        categoryService.delete(Long.parseLong(id));
        return "redirect:/categories";
    }
    
}
