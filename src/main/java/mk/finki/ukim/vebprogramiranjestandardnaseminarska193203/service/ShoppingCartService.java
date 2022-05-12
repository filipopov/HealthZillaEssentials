package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.Product;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.ShoppingCart;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.ShoppingCartStatus;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    List<ShoppingCart> findAll();

    List<ShoppingCart> findAllByUser(User user);

    Optional<ShoppingCart> findById(Long id);

    void deleteProductById(Long id);

    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);

    ShoppingCart save(ShoppingCart shoppingCart);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

    List<Product> listAllProductsInShoppingCart(Long id);

    Integer getTotal(String username);

    void emptyShoppingCart(String username);

    void getProductByIdFromActiveShoppingCart(Long id, String remoteUser);
}
