package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Category;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Manufacturer;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.CategoryRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ProductRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Long id, String name, String description, Category category,
                        Integer price, Integer availablePieces, Manufacturer manufacturer) {
        if(this.productRepository.findById(id).isPresent()) {
            Product product = this.productRepository.getById(id);
            product.setName(name);
            product.setDescription(description);
            List<Category> categoryList = new ArrayList<>();
            categoryList.add(category);
            product.setCategories(categoryList);
            product.setPrice(price);
            product.setAvailablePieces(availablePieces);
            product.setManufacturer(manufacturer);
            return this.productRepository.save(product);
        }
        return this.productRepository.save(new Product(name, description, category, price, availablePieces, manufacturer));
    }

    public Product save(Product product){
        return this.productRepository.save(product);
    }

    @Override
    public Product save(Long id, Category category) {
        Product product = this.productRepository.getById(id);
        List<Category> categoryList = product.getCategories();
        categoryList.add(category);
        product.setCategories(categoryList);

        return this.productRepository.save(product);
    }

    @Override
    public Product save(Long productId, Long categoryId){
        Product product = this.productRepository.getById(productId);
        List<Category> categoryList = product.getCategories();
        if(this.categoryRepository.findById(categoryId).isPresent())
            if(!categoryList.contains(this.categoryRepository.findById(categoryId).get()))
                categoryList.add(this.categoryRepository.findById(categoryId).get());

        product.setCategories(categoryList);
        return this.productRepository.save(product);
    }

    @Override
    public List<Product> findAll(){ return this.productRepository.findAll(); }

    @Override
    public List<Product> findAllAvailable() { return this.productRepository.findAll().stream().filter(i->i.getAvailablePieces()>0).collect(Collectors.toList()); }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByNameLike(String name) {
        return this.productRepository.findAllByNameLike(name);
    }

    @Override
    public List<Product> search(String search) {
        List<Product> list = this.productRepository.findAll();
        return list.stream().filter(i->i.getName().contains(search)).collect(Collectors.toList());
    }



}
