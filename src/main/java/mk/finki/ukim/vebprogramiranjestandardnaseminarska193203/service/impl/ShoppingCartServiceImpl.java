package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.ProductNotFoundException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.ShoppingCartNotFoundException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.UserNotFoundException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ProductRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.ShoppingCartRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.UserRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public List<ShoppingCart> findAllByUser(User user) {
        return this.shoppingCartRepository.findAllByUser(user);
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return this.shoppingCartRepository.findById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status) {
        return this.shoppingCartRepository.findByUserAndStatus(user, status);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        return this.shoppingCartRepository
                        .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                        .orElseGet(()->{
                            ShoppingCart cart = new ShoppingCart(user);
                            return this.shoppingCartRepository.save(cart);
                        });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        if(shoppingCart.getProducts().stream().filter(i->i.getId().equals(productId)).collect(Collectors.toList()).size()>0)
            throw new ProductAlreadyInShoppingCartException(productId);
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long id) {
        if(!this.shoppingCartRepository.findById(id).isPresent())
            throw new ShoppingCartNotFoundException(id);
        return shoppingCartRepository.findById(id).get().getProducts();
    }

    @Override
    public Integer getTotal(String username) {
        List<Product> products = this.getActiveShoppingCart(username).getProducts();
        int sum = 0;
        for (Product product : products) {
            if(product.getDiscount()==null)
                sum += product.getPrice();
            else
                sum+=product.getDiscountedPrice();
        }

        return sum;
    }

    public Integer getTotal(Long id) {
        List<Product> products = this.findById(id).get().getProducts();
        int sum = 0;
        for (Product product : products) sum += product.getPrice();

        return sum;
    }

    @Override
    public void emptyShoppingCart(String username) {
        //this.getActiveShoppingCart(username).setProducts(new ArrayList<>());
        this.getActiveShoppingCart(username).setStatus(ShoppingCartStatus.FINISHED);
        this.save(this.getActiveShoppingCart(username));
    }

    @Override
    public void getProductByIdFromActiveShoppingCart(Long id, String username){
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        List<Product> newProducts = shoppingCart.getProducts().stream().filter(i->!i.getId().equals(id)).collect(Collectors.toList());
        shoppingCart.setProducts(newProducts);
        this.shoppingCartRepository.save(shoppingCart);
    }
}
