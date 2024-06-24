package myfinances.category;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll().stream().sorted(Comparator.comparing(Category::getName)).toList();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public void save(Category category) {
        category.setName(category.getName().toUpperCase());
        categoryRepository.save(category);
    }
}
