package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Long id, String name, String description, Category category,
                        Integer price, Integer availablePieces, Manufacturer manufacturer);
    Product save(Product product);
    Product save(Long productId, Long categoryId);
    Product save(Long id, Category category);
    List<Product> findAllAvailable();
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void deleteById(Long id);
    List<Product> findAllByNameLike(String name);
    List<Product> search(String search);
}
