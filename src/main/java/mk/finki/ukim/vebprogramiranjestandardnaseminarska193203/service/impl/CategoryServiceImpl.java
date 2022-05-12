package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.CategoryRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category save(Long id, String name, String description, List<Product> products) {
        if(this.categoryRepository.findById(id).isPresent()) {
            Category category = this.categoryRepository.getById(id);
            category.setName(name);
            category.setCategoryDescription(description);
            category.setProducts(products);
            return this.categoryRepository.save(category);
        }
        return this.categoryRepository.save(new Category(name, description, products));
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
