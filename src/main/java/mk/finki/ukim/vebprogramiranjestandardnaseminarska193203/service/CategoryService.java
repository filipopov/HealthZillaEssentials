package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    Category save(Long id, String name, String description, List<Product> products);
    List<Category> findAll();
    Optional<Category> findById(Long id);
    void deleteById(Long id);
}
